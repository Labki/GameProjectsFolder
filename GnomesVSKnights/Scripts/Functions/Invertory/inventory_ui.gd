extends Control

class_name InvUI

@onready var slots: Array = $NinePatchRect/GridContainer.get_children()
@export var base_character: BaseCharacter

var inv: Inventory
var is_open = false

func _ready():
	inv = base_character.Inv
	update_slots()
	toggle()

func update_slots():
	for i in range(min(inv.items.size(), slots.size())):
		slots[i].update(inv.items[i])

func _process(delta):
	pass
	
func toggle():
	if is_open:
		self.visible = true
		is_open = false
	else:
		self.visible = false
		is_open = true

func add_item(item_name: String, quantity: int = 1):
	pass

func remove_item(item_name: String, quantity: int = 1):
	pass
