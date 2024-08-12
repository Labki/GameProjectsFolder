extends State

class_name ChaseState

func enter(_owner):
	state_owner = _owner
	if state_owner.patrol_points.size() > 0:
		state_owner.current_point = 0

func update(_delta):
	var character = state_owner.character
	var player_position = state_owner.player.position
	var enemy_position = state_owner.position + character.position

	if state_owner.target == null:
		state_owner.direction = (player_position - enemy_position).normalized()
		state_owner.move()
	else:
		state_owner.change_state(state_owner.attack_state)

func exit():
	pass
