extends Control

class_name InventoryUI

@onready var grid_container = $NinePatchRect/GridContainer
@export var base_character: BaseCharacter
@export var slot_scene: PackedScene

var slots: Array = []
var inv: Inventory
var is_open = false

func _ready():
	inv = base_character.inventory
	inv.update.connect(update_slots)
	generate_inventory_slots(inv.max_slots)
	update_slots()
	self.visible = false

func generate_inventory_slots(slot_count: int):
	for child in grid_container.get_children():
		child.queue_free()
	slots.clear()
	for i in range(slot_count):
		var slot_instance = slot_scene.instantiate()
		grid_container.add_child(slot_instance)
		slots.append(slot_instance)


func update_slots():
	for i in range(min(inv.slots.size(), slots.size())):
		slots[i].update(inv.slots[i])

func _process(delta):
	pass
	
func toggle():
	is_open = not is_open
	self.visible = is_open
