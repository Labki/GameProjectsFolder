extends Node

var current_dir: String = "side"
var flip: bool = false
	
func setMovement(node: CharacterBody2D, direction: Vector2, animator: AnimatedSprite2D, speed: int):
	if direction.x != 0 || direction.y != 0:
		if direction.x != 0:
			current_dir = "side"
			if direction.x > 0:
				flip = false
			elif direction.x < 0:
				flip = true
		else:
			current_dir = "back" if direction.y < 0 else "front"
		PlayAnimation.play(animator, "walk", current_dir, flip, true)
	else:
		PlayAnimation.play(animator, "idle", current_dir, flip, true)
	var target_velocity = direction.normalized() * speed
	node.velocity = node.velocity.lerp(target_velocity, 0.2)
	node.move_and_slide()
