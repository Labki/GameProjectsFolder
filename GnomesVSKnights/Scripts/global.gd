extends Node

# Preload the state scripts
var idle_state = preload("res://Scripts/Enemy/States/idle_state.gd")
var patrol_state = preload("res://Scripts/Enemy/States/patrol_state.gd")
var chase_state = preload("res://Scripts/Enemy/States/chase_state.gd")
var attack_state = preload("res://Scripts/Enemy/States/attack_state.gd")

var playAnimation = preload("res://Scripts/Functions/playAnimation.gd")

func _ready():
	pass
