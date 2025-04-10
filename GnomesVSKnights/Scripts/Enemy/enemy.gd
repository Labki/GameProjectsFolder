extends BaseCharacter

class_name Enemy

@export var patrol_route: NodePath
@export var pauseUponReachingPoint: bool = false

var patrol_points = []
var current_point = null

var state_machine = null
var patrol_state = null
var chase_state = null
var idle_state = null
var attack_state = null

func enter():
	_enter()
	# Continue with initialization
	if patrol_route:	
		var patrol_node = get_node(patrol_route)
		for child in patrol_node.get_children():
			if child is Node2D:
				patrol_points.append(child.position)

	idle_state = Global.idle_state.new()
	patrol_state = Global.patrol_state.new()
	chase_state = Global.chase_state.new()
	attack_state = Global.attack_state.new()
	
	state_machine = patrol_state
	state_machine.enter(self)

	# Signal Node Connection
	self.target_area.connect("body_entered", Callable(self, "_on_target_area_body_entered"))
	self.target_area.connect("body_exited", Callable(self, "_on_target_area_body_exited"))
	
func update(delta):
	_update(delta)
	if not is_alive:
		return
	state_machine.update(delta)

func move():
	CharacterMovement.setMovement(self, direction, animator, speed)

func _on_target_area_body_entered(body):
	if body.has_method("player"):
		target = body

func _on_target_area_body_exited(body):
	if body == target:
		target = null

func change_state(new_state):
	if state_machine:
		state_machine.exit()
	state_machine = new_state
	state_machine.enter(self)
	
func update_direction():
	if patrol_points.size() > 0:
		direction = (patrol_points[current_point] - position).normalized()
		
func enemy():
	pass
