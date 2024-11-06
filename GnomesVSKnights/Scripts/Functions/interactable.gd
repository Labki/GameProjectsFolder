extends Node2D

class_name Interactable

@export var interactable: bool = true
@export var collectable: bool = false
@export var merge_radius: float = 100.0
var area_node = null

signal interacted
signal collected(item)

func _init_interactable():
	pass # Override this in derived classes for extra stuff

func _ready():
	_init_interactable()
	if area_node:
		area_node.connect("body_entered", Callable(self, "_on_interact_area_body_entered"))
		area_node.connect("body_exited", Callable(self, "_on_interact_area_body_exited"))
	else:
		print("Warning: area_node is not assigned!")
	if collectable:
		add_to_group("droppedItem")
		merge_nearby_items()

func _on_interact_area_body_entered(body):
	if body.has_method("player"):
		body._on_item_area_entered(self)

func _on_interact_area_body_exited(body):
	if body.has_method("player"):
		body._on_item_area_exited(self)

func interact():
	if interactable:
		emit_signal("interacted")
		if collectable:
			collect()
		_interact()

func collect():
	_collect()
	emit_signal("collected", get_item(), get_amount())
	queue_free()

func _interact():
	pass # Override this in derived classes for extra stuff after interacting

func _collect():
	pass # Override this in derived classes for extra stuff before collecting

func get_amount() -> int:
	return 1 # Override this in derived classes to get a specific amount per item

func set_amount(new_amount: int):
	pass  # Override in derived classes to set the item amount

func get_item() -> InventoryItem:
	return null  # Override this in derived classes to return specific item names

func make_pickupable():
	collectable = true
	add_to_group("droppedItem")

func place():
	collectable = false
	remove_from_group("droppedItem")

func merge_nearby_items():
	for other in get_tree().get_nodes_in_group("droppedItem"):
		if other != self and other.position.distance_to(position) < merge_radius:
			if get_item() == other.get_item():
				if get_amount() >= other.get_amount():
					set_amount(get_amount() + other.get_amount())
					other.queue_free()
				else:
					other.set_amount(other.get_amount() + get_amount())
					queue_free()
					return
