extends Node

class_name InputHelper

# Check if the run key is pressed
func is_running() -> bool:
	return Input.is_action_pressed("Run")

# Check if the attack key is pressed
func is_attacking() -> bool:
	return Input.is_action_just_pressed("Attack")

# Check if the interact key is pressed
func is_interacting() -> bool:
	return Input.is_action_just_pressed("Interact")

# Update player's speed based on input
func update_speed(character: BaseCharacter) -> void:
	if not character.is_attacking and is_running():
		character.speed = character.base_speed * 2
	else:
		character.speed = character.base_speed
