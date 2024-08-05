extends BaseCharacter

@onready var animator_node = $PlayerSprite

var attack_timer = 0.0
var attack_cooldown_duration = 1.0
var enemy: CharacterBody2D
var preventAnimation = false


func _ready():
	position = Vector2(125, 375)
	set_animator(animator_node)
	base_speed = speed
	attack_speed = speed / 10

func _physics_process(delta):
	if not is_alive:
		return

	var direction = Input.get_vector("moveLeft", "moveRight", "moveUp", "moveDown")
	CharacterMovement.setMovement(self, direction, animator, speed)
		
	if attack_timer > 0:
		attack_timer -= delta

func _input(event):
	if InputChecker.is_attacking() and attack_timer <= 0:
		attack(enemy)
		attack_timer = attack_cooldown_duration
		is_attacking = true
		speed = attack_speed
	InputChecker.update_speed(self)

func _on_hitbox_body_entered(body):
	if body.has_method("enemy"):
		enemy = body

func _on_hitbox_body_exited(body):
	if body.has_method("enemy"):
		enemy = null

func player():
	pass
