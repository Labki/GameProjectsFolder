extends CharacterBody2D

var MovementScript = preload("res://Scripts/movement.gd")
@onready var animator = $SlimeSprite
var newMovement: Node

var speed = 50
var player_chase = false
var player = null

func _ready():
	newMovement = MovementScript.new()
	
func _physics_process(delta):
	var direction = Vector2.ZERO
	if player_chase:
		direction = (player.position-position).normalized()
	newMovement.setMovement(self, direction, animator, speed)

func _on_detection_area_body_entered(body):
	player = body
	player_chase = true

func _on_detection_area_body_exited(body):
	player = null
	player_chase = false

func enemy():
	pass
