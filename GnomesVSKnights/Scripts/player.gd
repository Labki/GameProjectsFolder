extends CharacterBody2D

@export var speed: int = 100
var current_dir: String = "side"
var flipHor: bool = false
@onready var animator = $PlayerSprite
func _ready():
	animator.play(current_dir + "_idle")
	position = Vector2(125, 375)
	
func _process(delta):
	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	if direction.x != 0 || direction.y != 0:
		if direction.x != 0:
			current_dir = "side"
			if direction.x > 0:
				flipHor = false
			elif direction.x < 0:
				flipHor = true
		else:
			if direction.y < 0:
				current_dir = "back"
			elif direction.y > 0:
				current_dir = "front"
		play_anim("walk")
	else:
		play_anim("idle")
	velocity = direction * speed
	move_and_slide()

func play_anim(movement):
	if movement == "walk":
		animator.flip_h = flipHor
		animator.play(current_dir + "_" + movement)
	elif movement == "idle":
		animator.play(current_dir + "_" + movement)
