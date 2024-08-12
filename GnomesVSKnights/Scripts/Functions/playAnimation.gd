extends Node

var animation = ""
var animator = null
var character = null
var connected_animators = {}

func play(_animator, _animation, new_dir:= "", newFlip:= false, updateFlip:= false):	
	# Set animator, animation and character
	animation = _animation
	animator = _animator
	character = _animator.get_parent()

	if not character is CharacterBody2D:
		animator.play(animation)
		return

	if character.preventAnimation:
		return
		
	# Set variables for playing new animation
	if new_dir != "":
		character.direction = new_dir
	if updateFlip:
		character.flipHor = newFlip
	var anim_name = character.direction + "_" + animation
	if animation == "death":
		anim_name = animation
	if animation == "death" or animation == "attack":
		character.preventAnimation = true

	# Play animation based on animation name
	if animation == "walk":
		animator.flip_h = character.flipHor
	if animator.animation != anim_name:
		animator.play(anim_name)

func _on_animation_looped():
	pass
	
func _on_animation_finished():
	if animation == "attack":
		character.preventAnimation = false 
		if character.has_method("on_attack_animation_finished"):
			character.on_attack_animation_finished()
	if animation == "death":
		if character.has_method("on_death_animation_finished"):
			character.on_death_animation_finished()
