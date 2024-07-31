extends CharacterBody2D

@export var speed: int = 150
var current_dir: String = "dirRight"
func _ready():
	$KnightSprite.play("idle")
	position = Vector2(150, 360)
	
func _process(delta):
	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	if direction.x != 0 || direction.y != 0:
		if direction.x > 0 || direction.y < 0:
			current_dir = "dirRight"
		elif direction.x < 0 || direction.y > 0:
			current_dir = "dirLeft"
		play_anim(1)
	else:
		play_anim(0)
	velocity = direction * speed
	move_and_slide()

func play_anim(movement):
	var anim = $KnightSprite
	anim.flip_h = current_dir == "dirLeft"
	if movement == 1:
		anim.play("run")
	elif movement == 0:
		anim.play("idle")
