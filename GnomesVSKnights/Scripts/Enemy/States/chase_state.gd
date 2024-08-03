extends State

class_name ChaseState

func enter(_owner):
	state_owner = _owner

func update(_delta):
	if state_owner.player:
		state_owner.direction = (state_owner.player.position - state_owner.position).normalized()
		state_owner.move()

func exit():
	pass
