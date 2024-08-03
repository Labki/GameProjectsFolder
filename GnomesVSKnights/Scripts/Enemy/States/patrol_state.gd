extends State

class_name PatrolState

func enter(_owner):
	state_owner = _owner
	state_owner.current_point = 0
	if state_owner.patrol_points.size() > 0:
		state_owner.direction = (state_owner.patrol_points[state_owner.current_point] - state_owner.character.position).normalized()
		print(state_owner.direction)

func update(_delta):
	if state_owner.patrol_points.size() == 0:
		return

	if state_owner.character.position.distance_to(state_owner.patrol_points[state_owner.current_point]) < 10:
		state_owner.current_point = (state_owner.current_point + 1) % state_owner.patrol_points.size()
		state_owner.direction = (state_owner.patrol_points[state_owner.current_point] - state_owner.character.position).normalized()
		print(state_owner.direction)
		
	state_owner.move()

func exit():
	pass
