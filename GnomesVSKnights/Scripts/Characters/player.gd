extends CharacterBody2D

var health = 100
var player_alive = true
var attack_timer = 0.0
var attack_cooldown_duration = 1.0
var is_attacking = false 
var enemy: CharacterBody2D
var preventAnimation = false

@export var speed: int = 100
@onready var animator = $PlayerSprite
var base_speed = speed
var attack_speed = speed / 10

var PlayAnimation = Global.playAnimation.new()

func _ready():
	position = Vector2(125, 375)
	animator.connect("animation_finished", Callable(PlayAnimation, "_on_animation_finished"))
	animator.connect("animation_looped", Callable(PlayAnimation, "_on_animation_looped"))
func _physics_process(delta):
	if not player_alive:
		return

	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	if not is_attacking:
		CharacterMovement.setMovement(self, direction, animator, speed)

	if health <= 0:
		player_alive = false
		health = 0
		self.queue_free()
		
	if attack_timer > 0:
		attack_timer -= delta

func _input(event):
	if event.is_action_pressed("Attack") and attack_timer <= 0:
		attack(enemy)
		attack_timer = attack_cooldown_duration
		is_attacking = true
		speed = attack_speed
	if event.is_action_pressed("Run"):
		speed = base_speed * 2
	if event.is_action_released("Run"):
		speed = base_speed

func _on_hitbox_body_entered(body):
	if body.has_method("enemy"):
		enemy = body

func _on_hitbox_body_exited(body):
	if body.has_method("enemy"):
		enemy = null
		
func attack(enemy):
	PlayAnimation.play(animator, "attack")
	if enemy:
		enemy.health -= 10

func on_attack_animation_finished():
	speed = base_speed
	is_attacking = false
	
func on_death_animation_finished():
	queue_free() 
	
func player():
	pass
