extends BaseCharacter

@onready var animator_node = $SlimeSprite
@onready var detection_area = $DetectionArea
@onready var hitbox = $Hitbox
@onready var collision_shape = $CollisionShape2D
@export var preventAnimation = false

func _ready():
	set_animator(animator_node)
	pass

func _physics_process(delta):
	pass

func enemy():
	pass
