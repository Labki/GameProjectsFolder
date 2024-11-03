extends Interactable

@onready var animator = $AppleSprite
@export var item_data: InventoryItem = preload("res://Resources/Inventory/Items/apple.tres")
@export var item_amount: int = 1
var PlayAnimation = Global.playAnimation.new()

func _init_interactable():
	area_node = $InteractArea
	PlayAnimation.play(animator, "default")
	add_to_group("itemDropped")

func get_amount() -> int:
	return item_amount

func set_amount(new_amount: int):
	item_amount = new_amount

func get_item() -> InventoryItem:
	return item_data
