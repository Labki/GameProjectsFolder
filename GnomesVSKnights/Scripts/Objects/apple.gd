extends Interactable

@onready var animator = $AppleSprite

var PlayAnimation = Global.playAnimation.new()

func _ready():
	PlayAnimation.play(animator, "default")

func _on_interact_area_body_entered(body):
	if body.has_method("player"):
		player_in_range = true

func _on_interact_area_body_exited(body):
	if body.has_method("player"):
		player_in_range = false
