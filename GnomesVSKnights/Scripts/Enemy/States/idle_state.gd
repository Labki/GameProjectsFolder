extends State

class_name IdleState

@export var idle_duration: float = 2.0
var elapsed_time: float = 0.0

func enter(_owner):
	state_owner = _owner
	elapsed_time = 0.0
	# Stop the character's movement and let setMovement handle the idle animation
	state_owner.direction = Vector2()
	state_owner.move()

func update(delta):
	elapsed_time += delta
	if elapsed_time >= idle_duration:
		# Resume patrol or other actions after idle duration
		if state_owner.patrol_points.size() > 0:
			state_owner.update_direction()
			state_owner.change_state(state_owner.patrol_state)
		else:
			# Stay idle if no patrol points
			elapsed_time = 0.0

func exit():
	pass
