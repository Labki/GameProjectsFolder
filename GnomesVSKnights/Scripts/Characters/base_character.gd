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

# Child Nodes
var animator: AnimatedSprite2D

var PlayAnimation = Global.playAnimation.new()

# Set animator and connect signals
func set_animator(animator_node: AnimatedSprite2D) -> void:
	animator = animator_node
	if animator:
		animator.connect("animation_finished", Callable(PlayAnimation, "_on_animation_finished"))
		animator.connect("animation_looped", Callable(PlayAnimation, "_on_animation_looped"))

# Take Damage from attacker
func take_damage(amount: int) -> void:
	health -= amount
	if health <= 0:
		die()

# Heal
func heal(amount: int) -> void:
	health += amount
	if health > max_health:
		health = max_health

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
