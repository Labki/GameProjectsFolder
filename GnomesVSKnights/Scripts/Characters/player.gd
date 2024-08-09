extends BaseCharacter

@onready var animator_node: AnimatedSprite2D = $PlayerSprite
@onready var healthbar_node: ProgressBar = $HealthBar
@onready var inventory = $inventory

var attack_timer = 0.0
var attack_cooldown_duration = 1.0
var enemy: CharacterBody2D
var preventAnimation = false
var nearby_item = null 

func _ready():
	set_animator(animator_node)
	set_healthbar(healthbar_node)
	_onready()
	position = Vector2(125, 375)
	base_speed = speed
	attack_speed = speed / 10

func _physics_process(delta):
	if not is_alive:
		return

	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	CharacterMovement.setMovement(self, direction, animator, speed)
		
	if attack_timer > 0:
		attack_timer -= delta
		
	if InputChecker.is_interacting() and nearby_item:
		nearby_item.interact()

func _input(event):
	if InputChecker.is_attacking() and attack_timer <= 0:
		attack(enemy)
		attack_timer = attack_cooldown_duration
		is_attacking = true
		speed = attack_speed
	InputChecker.update_speed(self)

# Check for enemy
func _on_hitbox_body_entered(body):
	if body.has_method("enemy"):
		enemy = body

func _on_hitbox_body_exited(body):
	if body.has_method("enemy"):
		enemy = null

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
	print("Collected item:", item_name)

func player():
	pass
