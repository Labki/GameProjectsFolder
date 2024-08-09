extends Control

class_name Inventory  # This can stay

# A dictionary to hold the items in the inventory
var items = {}

# Reference to the VBoxContainer that holds the item labels
@onready var item_list_container = $VBoxContainer

# Method to add an item to the inventory
func add_item(item_name: String, quantity: int = 1):
	if items.has(item_name):
		items[item_name] += quantity
	else:
		items[item_name] = quantity
	update_inventory_display()

# Method to remove an item from the inventory
func remove_item(item_name: String, quantity: int = 1):
	if items.has(item_name):
		items[item_name] -= quantity
		if items[item_name] <= 0:
			items.erase(item_name)
		update_inventory_display()
	else:
		print("Item not found in inventory: %s" % item_name)

# Method to display the inventory in the UI
func update_inventory_display():
	if item_list_container == null:
		return
	for item_name in items.keys():
		var item_label = Label.new()
		item_label.text = "%s: x%d" % [item_name, items[item_name]]
		item_list_container.add_child(item_label)
