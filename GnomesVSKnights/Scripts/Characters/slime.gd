extends CharacterBody2D

@onready var animator = $SlimeSprite
@onready var detection_area = $DetectionArea
@onready var hitbox = $Hitbox
@onready var collision_shape = $CollisionShape2D

@export var speed: int = 50
@export var health: float = 100.0
@export var preventAnimation = false

func _ready():
	pass

func _physics_process(delta):
	pass

func enemy():
	pass
