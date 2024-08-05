extends Node

var current_dir: String = "side"
var flipHor: bool = false
var animation = ""
var animator = null
var character = null
var connected_animators = {}

func play(_animator, _animation, new_dir:= "", newFlip:= false, updateFlip:= false):	
	# Set animator, animation and character for connceted functions
	animation = _animation
	animator = _animator
	character = _animator.get_parent()
	
	if character.preventAnimation:
		return
		
	# Set variables for playing new animation
	if new_dir != "":
		current_dir = new_dir
	if updateFlip:
		flipHor = newFlip
	var anim_name = current_dir + "_" + animation
	if animation == "death":
		anim_name = animation
	if animation == "death" or animation == "attack":
		character.preventAnimation = true

	# Play animation based on animation name
	if animation == "walk":
		animator.flip_h = flipHor
	if animator.animation != anim_name:
		animator.play(anim_name)

func _on_animation_looped():
	pass
	
func _on_animation_finished():
	var node = character.get_parent() if character.get_parent().has_method("enemy") else character
	if animation == "attack":
		character.preventAnimation = false 
		if node.has_method("on_attack_animation_finished"):
			node.on_attack_animation_finished()
	if animation == "death":
		if node.has_method("on_death_animation_finished"):
			node.on_death_animation_finished()
