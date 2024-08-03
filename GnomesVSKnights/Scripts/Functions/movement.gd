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
	var target_velocity = direction.normalized() * speed
	node.velocity = node.velocity.lerp(target_velocity, 0.1)
	node.move_and_slide()

func play_anim(movement, animator):
	var anim_name = current_dir + "_" + movement
	if movement == "walk":
		animator.flip_h = flipHor
	if animator.animation != anim_name:
		animator.play(anim_name)
