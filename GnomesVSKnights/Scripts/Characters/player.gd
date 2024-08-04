extends CharacterBody2D

var enemy_inRange = false
var attack_cooldown = true
var health = 100
var player_alive = true

@export var speed: int = 100
@onready var animator = $PlayerSprite
var newMovement: Node

func _ready():
	position = Vector2(125, 375)
	
func _physics_process(delta):
	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	CharacterMovement.setMovement(self, direction, animator, speed)
	
	if health <= 0:
		player_alive = false
		health = 0
		print("Player has been killed")
		self.queue_free()

func _input(event):
	if event.is_action_pressed("Attack"):
		print("left click")
	if event.is_action_pressed("Run"):
		speed += speed
	if event.is_action_released("Run"):
		speed = speed/2
	if event.is_action_pressed("Crouch"):
		print("Crouch")
	if event.is_action_pressed("Jump"):
		print("Jump")

func _on_hitbox_body_entered(body):
	if body.has_method("enemy"):
		enemy_inRange = true

func _on_hitbox_body_exited(body):
	if body.has_method("enemy"):
		enemy_inRange = false

func player():
	pass
