extends Interactable

@onready var animator = $AppleSprite
var PlayAnimation = Global.playAnimation.new()

func _init_interactable():
	area_node = $InteractArea
	PlayAnimation.play(animator, "default")

func get_item_name():
	return "Apple" 
