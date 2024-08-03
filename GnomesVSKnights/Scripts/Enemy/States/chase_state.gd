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

	# Calculate direction towards player
	state_owner.direction = (player_position - enemy_position).normalized()
	state_owner.move()

func exit():
	pass
