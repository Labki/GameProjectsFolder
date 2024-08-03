extends Node

# Preload the state scripts
var patrol_state = preload("res://Scripts/Enemy/States/patrol_state.gd")
var chase_state = preload("res://Scripts/Enemy/States/chase_state.gd")
var idle_state = preload("res://Scripts/Enemy/States/idle_state.gd")

func _ready():
	pass
