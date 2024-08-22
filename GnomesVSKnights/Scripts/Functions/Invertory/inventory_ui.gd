extends Control

class_name InventoryUI

@onready var slots: Array = $NinePatchRect/GridContainer.get_children()
@export var base_character: BaseCharacter

var inv: Inventory
var is_open = false

func _ready():
	inv = base_character.inventory
	inv.update.connect(update_slots)
	update_slots()
	self.visible = false

func update_slots():
	for i in range(min(inv.slots.size(), slots.size())):
		slots[i].update(inv.slots[i])

func _process(delta):
	pass
	
func toggle():
	is_open = not is_open
	self.visible = is_open
