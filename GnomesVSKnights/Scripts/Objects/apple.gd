extends Interactable

@onready var animator = $AppleSprite
@export var item_data: InventoryItem = preload("res://Resources/Inventory/Items/apple.tres")
@export var item_amount: int = 1
@export var merge_radius: float = 100.0
var PlayAnimation = Global.playAnimation.new()

func _init_interactable():
	area_node = $InteractArea
	PlayAnimation.play(animator, "default")
	add_to_group("apples")
	merge_nearby_apples()  

func merge_nearby_apples():
	for other in get_tree().get_nodes_in_group("apples"):
		if other != self and other.position.distance_to(position) < merge_radius:
			if item_amount >= other.get_amount():
				item_amount += other.get_amount()
				other.queue_free()
			else:
				other.item_amount += item_amount
				queue_free()
				return

func get_amount() -> int:
	return item_amount

func get_item() -> InventoryItem:
	return item_data
