extends Node

# Preload the state scripts
var patrol_state = preload("res://Scripts/Enemy/States/patrol_state.gd")
var chase_state = preload("res://Scripts/Enemy/States/chase_state.gd")
var idle_state = preload("res://Scripts/Enemy/States/idle_state.gd")

var playAnimation = preload("res://Scripts/Functions/playAnimation.gd")
var inventory = preload("res://Scripts/Functions/inventory.gd")

func _ready():
	pass
