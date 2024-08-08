extends Interactable

@export var flaming: bool = false
@onready var animator = $CampfireSprite

var PlayAnimation = Global.playAnimation.new()
var player_in_range = false

func _ready():
	animation()

func _on_area_2d_body_entered(body):
	if body.name == "Player":
		player_in_range = true

func _on_area_2d_body_exited(body):
	if body.name == "Player":
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
