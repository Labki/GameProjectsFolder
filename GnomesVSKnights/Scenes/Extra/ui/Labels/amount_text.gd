extends Label

# Load the FontVariation resource
var resFont: FontVariation = preload("res://Assets/Fonts/uarFont.tres").duplicate()
@export var FontDescale = 300  # Scaling factor for adjusting font size

func _ready() -> void:
	# Apply the FontVariation to the Label using theme override
	add_theme_font_override("font", resFont)
	# Set an initial font size if needed
	var initial_font_size = calculate_font_size()
	add_theme_font_size_override("font_size", initial_font_size)

func _physics_process(delta) -> void:
	# Dynamically update the font size based on window size
	var new_font_size = calculate_font_size()
	add_theme_font_size_override("font_size", new_font_size)

# Function to calculate the font size based on window size
func calculate_font_size() -> int:
	var window_size = DisplayServer.window_get_size()
	# Calculate font size based on the window dimensions and scaling factor
	return int((window_size.x / FontDescale) + (window_size.y / FontDescale))
