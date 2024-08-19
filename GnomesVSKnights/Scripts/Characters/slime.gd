extends Enemy

@onready var animator_node: AnimatedSprite2D = $sprite
@onready var healthbar_node: ProgressBar = $health_bar
@onready var target_area: Area2D = $target_area
@onready var collision_shape = $collision

func _ready():
	set_animator(animator_node)
	set_healthbar(healthbar_node)
	enter()
	pass

func _physics_process(delta):
	update(delta)
	pass
