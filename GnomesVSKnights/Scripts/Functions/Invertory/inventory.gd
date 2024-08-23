extends Resource

class_name Inventory

signal update

@export var slots: Array[InventorySlot] = []
@export var max_slots: int = 18
@export var max_stack_size: int = 81

func add_item(item: InventoryItem, amount: int = 1):
	for slot in slots:
		if slot.item == item and slot.amount < max_stack_size:
			var space_in_stack = max_stack_size - slot.amount
			if amount <= space_in_stack:
				slot.amount += amount
				amount = 0
				break
			else: 
				slot.amount += space_in_stack
				amount -= space_in_stack

	while amount > 0:
		var empty_slot = slots.filter(func(slot): return slot.item == null).pop_back()
		if empty_slot:
			if amount <= max_stack_size:
				empty_slot.item = item
				empty_slot.amount = amount
				amount = 0
			else:
				empty_slot.item = item
				empty_slot.amount = max_stack_size
				amount -= max_stack_size	
	update.emit()
	
func remove_item(item: InventoryItem, amount: int = 1):
	var remaining_amount = amount
	
	for slot in slots:
		if slot.item == item:
			if slot.amount > remaining_amount:
				slot.amount -= remaining_amount
				remaining_amount = 0
			else:
				remaining_amount -= slot.amount
				slot.item = null
				slot.amount = 0
			if remaining_amount == 0:
				break
	update.emit()

func initialize_slots(slot_count: int):
	slots.clear()
	for i in range(slot_count):
		slots.append(InventorySlot.new())
	update.emit()
