extends Node2D

@export var enemy_character: NodePath
@export var patrol_route: NodePath
@export var speed: int

var player = null
var patrol_points = []
var current_point = 0
var direction = Vector2()
var state_machine = null
var patrol_state = null
var chase_state = null
var character: CharacterBody2D

func _ready():
	character = get_node(enemy_character)
	var patrol_node = get_node(patrol_route)
	
	if speed == 0:
		speed = character.speed

	if patrol_node:
		for child in patrol_node.get_children():
			if child is Node2D:
				patrol_points.append(child.position)

	patrol_state = Global.patrol_state.new()
	chase_state = Global.chase_state.new()
	
	state_machine = patrol_state
	state_machine.enter(self)

func _physics_process(delta):
	state_machine.update(delta)

func move():
	CharacterMovement.setMovement(character, direction, character.animator, speed)

func _on_detection_area_body_entered(body):
	if body.has_method("player"):  # Ensure the body is the player
		player = body
		change_state(chase_state)

func _on_detection_area_body_exited(body):
	if body.has_method("player"):  # Ensure the body is the player
		player = null
		change_state(patrol_state)

func change_state(new_state):
	state_machine.exit()
	state_machine = new_state
	state_machine.enter(self)

func enemy():
	pass
