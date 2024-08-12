extends Node

var PlayAnimation = Global.playAnimation.new()

func setMovement(node: CharacterBody2D, direction: Vector2, animator: AnimatedSprite2D, speed: int):
	if direction.x != 0 || direction.y != 0:
		if direction.x != 0:
			if direction.x > 0:
				node.direction = "right"
			elif direction.x < 0:
				node.direction = "left"
		else:
			node.direction = "back" if direction.y < 0 else "front"
		PlayAnimation.play(animator, "walk", node.direction)
	else:
		PlayAnimation.play(animator, "idle", node.direction)
	var target_velocity = direction.normalized() * speed
	node.velocity = node.velocity.lerp(target_velocity, 0.2)
	node.move_and_slide()
