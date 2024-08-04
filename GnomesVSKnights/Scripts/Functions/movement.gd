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
		PlayAnimation.play(animator, current_dir, "walk", flipHor)
	else:
		PlayAnimation.play(animator, current_dir, "idle", flipHor)
	var target_velocity = direction.normalized() * speed
	node.velocity = node.velocity.lerp(target_velocity, 0.2)
	node.move_and_slide()
