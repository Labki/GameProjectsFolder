extends Interactable

@export var flaming: bool = false
@onready var animator = $CampfireSprite
var PlayAnimation = Global.playAnimation.new()

func _init_interactable():
	area_node = $InteractArea
	animation()

# Override the interact method to add specific behavior
func _interact():
	flaming = not flaming
	animation()

# Play the animation
func animation() -> void:
	if flaming:
		PlayAnimation.play(animator, "on")
	else:
		PlayAnimation.play(animator, "off")
