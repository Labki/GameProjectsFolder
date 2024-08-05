extends Node2D

@export var enemy_character: NodePath
@export var patrol_route: NodePath
@export var speed: int
@export var pauseUponReachingPoint: bool = false

var player = null
var patrol_points = []
var current_point = null
var direction = Vector2()
var state_machine = null
var patrol_state = null
var chase_state = null
var idle_state = null
var character: CharacterBody2D
var enemy_alive = true

var PlayAnimation = Global.playAnimation.new()

func _ready():
	if not enemy_character:
		print("Error: enemy_character not set")
		return
	character = get_node(enemy_character)
	# Continue with initialization
	var patrol_node = get_node(patrol_route)
	
	if speed == 0:
		speed = character.speed

	if patrol_node:
		for child in patrol_node.get_children():
			if child is Node2D:
				patrol_points.append(child.position)

	patrol_state = Global.patrol_state.new()
	chase_state = Global.chase_state.new()
	idle_state = Global.idle_state.new()
	
	state_machine = patrol_state
	state_machine.enter(self)

	# Signal Node Connection
	character.detection_area.connect("body_entered", Callable(self, "_on_detection_area_body_entered"))
	character.detection_area.connect("body_exited", Callable(self, "_on_detection_area_body_exited"))
	character.animator.connect("animation_finished", Callable(PlayAnimation, "_on_animation_finished"))
	character.animator.connect("animation_looped", Callable(PlayAnimation, "_on_animation_looped"))
	
func _physics_process(delta):
	if not enemy_alive:
		return
	state_machine.update(delta)
	if character.health <= 0:
		die()

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
	if state_machine:
		state_machine.exit()
	state_machine = new_state
	state_machine.enter(self)
	
func update_direction():
	if patrol_points.size() > 0:
		direction = (patrol_points[current_point] - character.position).normalized()

func take_damage(amount):
	character.health -= amount
	if character.health <= 0:
		character.health = 0
		die()

func die():
	enemy_alive = false
	PlayAnimation.play(character.animator, "death")
	
func on_attack_animation_finished():
	pass

func on_death_animation_finished():
	queue_free()
		
func enemy():
	pass
