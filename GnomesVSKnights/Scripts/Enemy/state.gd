extends Node

class_name State

var state_owner

func enter(_owner):
	state_owner = _owner

func exit():
	pass

func update(_delta):
	pass
