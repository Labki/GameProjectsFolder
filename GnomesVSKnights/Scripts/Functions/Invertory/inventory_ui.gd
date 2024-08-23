extends Control

class_name InventoryUI

@onready var grid_container = $NinePatchRect/PCont/GCont
@onready var panel_container = $NinePatchRect/PCont
@export var base_character: BaseCharacter
@export var slot_scene: PackedScene
@export var inventory_padding: int = 5

var slots: Array = []
var inv: Inventory
var is_open = false

func _ready():
	inv = base_character.inventory
	inv.update.connect(update_slots)
	generate_inventory_slots(inv.max_slots)
	update_slots()
	self.visible = false
	print($NinePatchRect.custom_minimum_size)
	print(slot_scene, grid_container.columns, panel_container.size)

func generate_inventory_slots(slot_count: int):
	for child in grid_container.get_children():
		child.queue_free()
	slots.clear()
	
	if inv.slots.size() < slot_count:
		inv.slots.resize(slot_count)
		
	for i in range(slot_count):
		var slot_instance = slot_scene.instantiate()
		grid_container.add_child(slot_instance)
		slots.append(slot_instance)
		
		if inv.slots[i] == null:
			var new_slot = InventorySlot.new()
			new_slot.item = null
			new_slot.amount = 0
			inv.slots[i] = new_slot

	# Resize the NinePatchRect after generating the slots
	resize_ninepatchrect()

func resize_ninepatchrect():
	var total_width = 0
	var total_height = 0
	var columns = grid_container.columns
	var h_separation = grid_container.get("theme_override_constants/h_separation")
	var v_separation = grid_container.get("theme_override_constants/v_separation")
	var row_count = ceil(float(slots.size() / columns))

	if slots.size() > 0:
		var slot_size = slots[0].size
		total_width = columns * slot_size.x + (columns - 1) * h_separation + inventory_padding * 2
		total_height = row_count * slot_size.y + (row_count - 1) * v_separation + inventory_padding * 2

	$NinePatchRect.custom_minimum_size = Vector2(total_width, total_height)

func update_slots():
	for i in range(min(inv.slots.size(), slots.size())):
		slots[i].update(inv.slots[i])

func _process(delta):
	pass
	
func toggle():
	is_open = not is_open
	self.visible = is_open
