extends State

class_name ChaseState

func enter(_owner):
	state_owner = _owner
	if state_owner.patrol_points.size() > 0:
		state_owner.current_point = 0

func update(_delta):
	var player_position = state_owner.player.position

	if state_owner.target == null:
		state_owner.direction = (player_position - state_owner.position).normalized()
		state_owner.move()
	else:
		state_owner.change_state(state_owner.attack_state)

func exit():
	pass
