extends Node

var current_dir: String = "side"
var flipHor: bool = false
	
func setMovement(node: CharacterBody2D, direction: Vector2, animator: AnimatedSprite2D, speed: int):
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
		play_anim("walk", animator)
	else:
		play_anim("idle", animator)
	node.velocity = direction.normalized() * speed
	node.move_and_slide()

func play_anim(movement, animator):
	if movement == "walk":
		animator.flip_h = flipHor
		animator.play(current_dir + "_" + movement)
	elif movement == "idle":
		animator.play(current_dir + "_" + movement)
