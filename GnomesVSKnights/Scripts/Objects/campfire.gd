extends Interactable

@export var flaming: bool = false
@onready var animator = $CampfireSprite

var PlayAnimation = Global.playAnimation.new()

func _ready():
	animation()

func _on_interact_area_body_entered(body):
	if body.has_method("player"):
		player_in_range = true

func _on_interact_area_body_exited(body):
	if body.has_method("player"):
		player_in_range = false

# Override the interact method to add specific behavior
func interact():
	if player_in_range:
		# Call the base interact method logic directly
		if interactable:
			emit_signal("interacted")
			if collectable:
				collect()
		flaming = not flaming
		animation()

# Play the animation
func animation() -> void:
	if flaming:
		PlayAnimation.play(animator, "on")
	else:
		PlayAnimation.play(animator, "off")
