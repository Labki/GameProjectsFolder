extends Node

var PlayAnimation = Global.playAnimation.new()

func setMovement(node: CharacterBody2D, direction: Vector2, animator: AnimatedSprite2D, speed: int):
	if direction.x != 0 || direction.y != 0:
		if direction.x > 0.5 || direction.x < -0.5:
			node.current_dir = "right" if direction.x > 0.5 else "left"
		elif direction.y != 0 && direction.x < 0.5 && direction.x > -0.5:
			node.current_dir = "back" if direction.y < 0 else "front"
		PlayAnimation.play(animator, "walk", node.current_dir)
	else:
		PlayAnimation.play(animator, "idle", node.current_dir)
	var target_velocity = direction.normalized() * speed
	node.velocity = node.velocity.lerp(target_velocity, 0.2)
	node.move_and_slide()
