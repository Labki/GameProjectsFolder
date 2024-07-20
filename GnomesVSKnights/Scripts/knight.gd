extends CharacterBody2D

@export var speed: int = 500

func _ready():
	position = Vector2(150, 360)
	
func _process(delta):
	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	velocity = direction * speed
	move_and_slide()
