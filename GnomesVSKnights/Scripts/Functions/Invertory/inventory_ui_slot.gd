extends Panel

@onready var item_display: Sprite2D = $CenterContainer/Panel/item_display
@onready var amount_label: Label = $CenterContainer/Panel/Label
func update(slot: InventorySlot):
	if !slot.item:
		item_display.visible = false
		amount_label.visible = false
	else:
		item_display.visible = true
		item_display.texture = slot.item.texture
		if slot.amount > 1:
			amount_label.visible = true
		amount_label.text = str(slot.amount)
