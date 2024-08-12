# Scripts/Characters/base_character.gd
extends CharacterBody2D

class_name BaseCharacter

# Exports
@export var health: int = 100
@export var max_health: int = 100
@export var attack_power: int = 10
@export var speed: int = 50

# Different types of speed
var base_speed: int
var attack_speed: int

# Character state
var is_attacking = false
var is_alive = true
var is_running = false

# Direction of character
var direction: String
var flipHor: bool

# Child Nodes
var animator: AnimatedSprite2D
var healthbar: ProgressBar
var health_timer: Timer

var PlayAnimation = Global.playAnimation.new()

func _onready():
	direction = "side"
	flipHor = false
	update_health()

# Set outiside nodes
func set_animator(animator_node: AnimatedSprite2D) -> void:
	animator = animator_node
	if animator:
		animator.connect("animation_finished", Callable(PlayAnimation, "_on_animation_finished"))
		animator.connect("animation_looped", Callable(PlayAnimation, "_on_animation_looped"))

func set_healthbar(healthbar_node: ProgressBar) -> void:
	healthbar = healthbar_node
	if healthbar:
		for child in healthbar.get_children():
			if child is Timer:
				health_timer = child
				break
		if health_timer:
			health_timer.connect("timeout", Callable(self, "_start_health_regen"))

# Take Damage from attacker
func take_damage(amount: int) -> void:
	health -= amount
	update_health()
	if health <= 0:
		die()

# Health
func heal(amount: int) -> void:
	health += amount
	update_health()
	if health > max_health:
		health = max_health

func update_health():
	healthbar.value = health * 100 / max_health
	if health >= max_health:
		healthbar.visible = false
	else:
		healthbar.visible = true
		
func _start_health_regen():
	if health < max_health and health > 0:
		self.heal(max_health / 10)

# Attack the target
func attack(target: BaseCharacter) -> void:
	PlayAnimation.play(animator, "attack")
	if target:
		target.take_damage(attack_power)

# The end of an era
func die():
	is_alive = false
	PlayAnimation.play(animator, "death")

# Attack animation done
func on_attack_animation_finished():
	if self.has_method("player"):
		InputChecker.update_speed(self)
		is_attacking = false
	else:
		pass

# Death animation done
func on_death_animation_finished():
	queue_free()
