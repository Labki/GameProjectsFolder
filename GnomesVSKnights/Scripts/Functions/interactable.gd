extends Node2D

class_name Interactable

# Exported variables
@export var interactable: bool = true
@export var collectable: bool = false

var player_in_range: bool = false

# Signals
signal interacted
signal collected

# This method will be called when the item is interacted with
func interact():
	if player_in_range and interactable:
		print("Interacted with item")
		emit_signal("interacted")
		if collectable:
			collect()
	else:
		print("Item is not interactable")

# This method will be called when the item is collected
func collect():
	if collectable:
		print("Collected item")
		emit_signal("collected")
		queue_free() # Remove item from the scene
	else:
		print("Item is not collectable")
