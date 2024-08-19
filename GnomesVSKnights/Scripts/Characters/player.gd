extends BaseCharacter

@onready var animator_node: AnimatedSprite2D = $sprite
@onready var healthbar_node: ProgressBar = $health_bar
@onready var attack_area_node: Area2D = $target_area
@onready var inventory = $inventory

var nearby_item = null
var targets_in_range: Array = []

func _ready():
	set_animator(animator_node)
	set_healthbar(healthbar_node)
	_enter()
	position = Vector2(125, 375)
	base_speed = speed
	attack_speed = speed / 10

func _physics_process(delta):
	if not is_alive:
		return
	_update(delta)
	direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	CharacterMovement.setMovement(self, direction, animator, speed)
	attack_area()
	
	if attack_timer > 0:
		attack_timer -= delta
		
	if InputChecker.is_interacting() and nearby_item:
		nearby_item.interact()

func _input(event):
	if not is_alive:
		return
	if InputChecker.is_attacking() and attack_timer <= 0:
		attack(target)
		attack_timer = attack_cooldown
		speed = attack_speed
	InputChecker.update_speed(self)
	
func attack_area(): 
	# Enable the specific node based on direction
	var specific_node = attack_area_node.get_node(current_dir)
	specific_node.disabled = false

	for child in attack_area_node.get_children():
		if not child == specific_node:
			child.disabled = true

# Check for target
func _on_target_area_body_entered(body):
	if body.is_in_group("Enemies"):
		targets_in_range.append(body)

func _on_target_area_body_exited(body):
	if body.is_in_group("Enemies"):
		targets_in_range.erase(body)

# Handle entering the interaction range of an item
func _on_item_area_entered(item):
	if item.has_method("connect"):
		item.connect("collected", Callable(self, "collect_item"))
		nearby_item = item

# Handle exiting the interaction range of an item
func _on_item_area_exited(item):
	if item.has_method("disconnect"):
		item.disconnect("collected", Callable(self, "collect_item"))
		nearby_item = null

func collect_item(item_name: String):
	inventory.add_item(item_name)

func player():
	pass
