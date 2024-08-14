extends Node

var animation = ""
var animator = null
var character = null
var connected_animators = {}

func play(_animator, _animation, new_dir:= ""):
	# Set animator, animation and character
	animation = _animation
	animator = _animator
	character = _animator.get_parent()

	if not character is CharacterBody2D:
		animator.play(animation)
		return
	if character.preventAnimation and character.is_alive:
		return

	# Set variables for playing new animation
	if new_dir != "":
		character.current_dir = new_dir
		animator.flip_h = true if new_dir == "left" else false

	var anim_dir = "side" if ((character.current_dir == "left") or (character.current_dir == "right")) else character.current_dir
	var anim_name = anim_dir + "_" + animation
	if animation == "death":
		anim_name = animation
	if animation == "attack":
		character.is_attacking = true
		animator.play(anim_name)
	elif animator.animation != anim_name:
		animator.play(anim_name)

func _on_animation_looped():
	pass
	
func _on_animation_finished():
	if animation == "attack":
		if character.has_method("on_attack_animation_finished"):
			character.on_attack_animation_finished()
	if animation == "death":
		if character.has_method("on_death_animation_finished"):
			character.on_death_animation_finished()
