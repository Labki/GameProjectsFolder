extends Interactable

@onready var animator = $AppleSprite
@export var item_data: InventoryItem = preload("res://Resources/Inventory/Items/apple.tres")
var PlayAnimation = Global.playAnimation.new()

func _init_interactable():
	area_node = $InteractArea
	PlayAnimation.play(animator, "default")

func get_item() -> InventoryItem:
	return item_data
