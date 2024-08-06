extends BaseCharacter

@onready var animator_node: AnimatedSprite2D = $SlimeSprite
@onready var healthbar_node: ProgressBar = $SlimeHealthBar
@onready var detection_area = $DetectionArea
@onready var hitbox = $Hitbox
@onready var collision_shape = $CollisionShape2D
@export var preventAnimation = false

func _ready():
	set_animator(animator_node)
	set_healthbar(healthbar_node)
	_onready()
	pass

func _physics_process(delta):
	pass

func enemy():
	pass
