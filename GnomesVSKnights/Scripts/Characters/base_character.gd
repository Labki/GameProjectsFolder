# Scripts/Characters/base_character.gd
extends CharacterBody2D

class_name BaseCharacter

#region Properties
# Exports
@export var health: float = 25
@export var max_health: float = 25
@export var regeneration: float = 2.5
@export var regen_cooldown: float = 8.0
@export var attack_power: float = 10
@export var attack_cooldown: float = 1.0
@export var attack_range: float = 25 
@export var hit_frame: int = 1
@export var speed: float = 100
@export var crit_rate: float = 0.1
@export var crit_dmg: float = 2.0
@export var level: int = 1
@export var experience: float = 0
@export var experience_to_next_level: int = 100
@export var experience_gain_rate: float = 1.0

# Internal variables
var base_speed: float
var attack_speed: float
var current_dir: String
var attack_timer: float = 0.0
var preventAnimation: bool = false
var target: BaseCharacter = null
var direction = Vector2()

# Character state
var is_alive: bool = true
var is_attacking: bool = false
var is_running: bool = false
var deny_movement: bool = false

# Child Nodes
var animator: AnimatedSprite2D
var health_timer: Timer
var damage_timer: Timer
#region Progress Bars
var healthbar
var manabar
var expbar
#endregion

var PlayAnimation = Global.playAnimation.new()
#endregion
#region Base Functions
func _enter():
	current_dir = "right"
	update_healthbar()
	update_expbar()

func _update(delta):
	var _delta = delta
	if is_attacking:
		preventAnimation = true
	else:
		preventAnimation = false

#endregion
#region Set Outside Nodes
# Set outiside nodes
func set_animator(animator_node: AnimatedSprite2D) -> void:
	animator = animator_node
	if animator:
		animator.connect("animation_finished", Callable(PlayAnimation, "_on_animation_finished"))
		animator.connect("animation_looped", Callable(PlayAnimation, "_on_animation_looped"))

func set_healthbar(healthbar_node) -> void:
	healthbar = healthbar_node
	if healthbar:
		for child in healthbar.get_children():
			if child.name == "regen_timer":
				health_timer = child
			if child.name == "damage_timer":
				damage_timer = child
				break
		if health_timer:
			health_timer.connect("timeout", Callable(self, "_start_health_regen"))
		if damage_timer:
			damage_timer.wait_time = regen_cooldown
			damage_timer.one_shot = true

func set_expbar(expbar_node) -> void:
	expbar = expbar_node

#endregion
#region Character Health
# Take Damage from attacker
func take_damage(amount: int) -> void:
	health -= amount
	update_healthbar()
	if damage_timer.is_stopped():
		damage_timer.start()
	else:
		damage_timer.stop()
		damage_timer.start()
	if health <= 0:
		die()

# Health
func heal(amount: int) -> void:
	health += amount
	update_healthbar()
	if health > max_health:
		health = max_health

func update_healthbar():
	healthbar.value = health * 100 / max_health
	if self.has_method("player"):
		var label = healthbar.get_node("amountText")
		label.text = str(health) + " / " + str(max_health) + " HP"
		return
	if health >= max_health:
		healthbar.visible = false
	else:
		healthbar.visible = true

func _start_health_regen():
	if health < max_health and health > 0 and damage_timer.is_stopped():
		heal(regeneration)
#endregion
#region Combat

# Determine if the attack is a critical hit
func is_critical_hit() -> bool:
	return randf() < crit_rate

func perform_attack(__target):
	if is_target_in_attack_range(__target) and __target:
		__target.take_damage(apply_damage(attack_power))
		if __target.health <= 0:
			gain_experience(__target)

func is_target_in_attack_range(newTarget) -> bool:
	if newTarget == null:
		return false
	return self.global_position.distance_to(newTarget.global_position) <= attack_range

func apply_damage(base_damage: float) -> float:
	var damage = base_damage
	if is_critical_hit():
		damage *= crit_dmg
	return damage

# Attack the target
func attack(_target: BaseCharacter) -> void:
	target = _target
	is_attacking = true
	PlayAnimation.play(animator, "attack")
	
	var hit_frame_reached = false
	var animation_name = animator.animation 
	var total_frames = animator.sprite_frames.get_frame_count(animation_name)
	
	while is_attacking:
		var current_frame = animator.frame
		if current_frame == hit_frame and not hit_frame_reached:
			if self.has_method("player"):
				for this in self.targets_in_range:
					perform_attack(this)
			else:
				perform_attack(target)
			hit_frame_reached = true

		if current_frame >= total_frames - 1:
			is_attacking = false

		await get_tree().process_frame
#endregion
#region Leveling System
func update_expbar():
	if expbar && self.has_method("player"):
		expbar.value = experience * 100 / experience_to_next_level
		var label = expbar.get_node("amountText")
		label.text = str(experience) + " / " + str(experience_to_next_level) + " EXP"

func gain_experience(target: BaseCharacter) -> void:
	experience += int(target.max_health * experience_gain_rate) # Example calculation, adjust as necessary
	if experience >= experience_to_next_level:
		level_up()
	update_expbar()

func level_up() -> void:
	level += 1
	experience -= experience_to_next_level
	experience_to_next_level = int(experience_to_next_level * 1.5) #Increase experience needed for the next level
	
	#Increase stats on level up
	max_health += 10
	attack_power += 2
	crit_rate += 0.01
	
	#Heal character to full health on level up
	#health = max_health
	#update_healthbar()
#endregion

# The end of an era
func die():
	is_alive = false
	PlayAnimation.play(animator, "death")

# Attack animation done
func on_attack_animation_finished():
	if has_method("player"):
		InputChecker.update_speed(self)
	else:
		pass
	is_attacking = false

# Death animation done
func on_death_animation_finished():
	queue_free()
