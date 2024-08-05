# Scripts/Characters/base_character.gd
extends CharacterBody2D

class_name BaseCharacter

@export var health: int = 100
@export var max_health: int = 100
@export var attack_power: int = 10
@export var speed: int = 50

var base_speed = speed
var is_attacking = false
var is_alive = true
var animator: AnimatedSprite2D

var PlayAnimation = Global.playAnimation.new()

func set_animator(animator_node: AnimatedSprite2D) -> void:
	animator = animator_node

func take_damage(amount: int) -> void:
	health -= amount
	if health <= 0:
		die()

func heal(amount: int) -> void:
	health += amount
	if health > max_health:
		health = max_health

func attack(target: BaseCharacter) -> void:
	PlayAnimation.play(animator, "attack")
	if target:
		target.take_damage(attack_power)

func die():
	is_alive = false
	PlayAnimation.play(animator, "death")
	
func on_attack_animation_finished():
	if self.has_method("player"):
		if Input.is_action_pressed("Run"):
			speed = base_speed * 2
		else:
			speed = base_speed
		is_attacking = false
	else:
		pass

func on_death_animation_finished():
	queue_free()
