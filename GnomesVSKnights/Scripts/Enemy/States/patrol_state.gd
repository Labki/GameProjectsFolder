extends State

class_name PatrolState

func enter(_owner):
	state_owner = _owner
	if state_owner.current_point == null:
		state_owner.current_point = 0
		state_owner.update_direction()

func update(_delta):
	if state_owner.patrol_points.size() == 0:
		state_owner.change_state(state_owner.idle_state)
		return
		
	var target_point = state_owner.patrol_points[state_owner.current_point]
	if state_owner.position.distance_to(target_point) < 10 + (state_owner.speed / 10):
		state_owner.current_point = (state_owner.current_point + 1) % state_owner.patrol_points.size()
		if state_owner.pauseUponReachingPoint:
			state_owner.change_state(state_owner.idle_state)
			return

	state_owner.update_direction()
	state_owner.move()
	
	if state_owner.target:
		state_owner.change_state(state_owner.chase_state)

func exit():
	pass
	
