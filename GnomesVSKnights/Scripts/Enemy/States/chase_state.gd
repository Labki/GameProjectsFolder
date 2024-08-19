extends State

class_name ChaseState

func enter(_owner):
	state_owner = _owner

func update(_delta):
	if state_owner.target == null:
		state_owner.change_state(state_owner.patrol_state)
		return

	var target_position = state_owner.target.global_position
	state_owner.direction = (target_position - state_owner.global_position).normalized()
	state_owner.move()

	if state_owner.is_target_in_attack_range():
		state_owner.change_state(state_owner.attack_state)

func exit():
	pass
