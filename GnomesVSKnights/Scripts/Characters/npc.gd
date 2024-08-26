extends BaseCharacter

class_name NPC

func enter():
	_enter()
	pass

func update(delta):
	_update(delta)
	if not is_alive:
		return
	handle_movement()

func handle_movement():
	direction = Vector2.ZERO
	CharacterMovement.setMovement(self, direction, animator, speed)

func npc():
	pass
