extends Node

var current_dir: String
var flipHor: bool = false

func play(animator, animation, new_dir:= "", newFlip:= false, updateFlip:= false):
	if new_dir != "":
		current_dir = new_dir
	if updateFlip:
		flipHor = newFlip
	var anim_name = animation if animation == "death" else current_dir + "_" + animation
	if animation == "walk":
		animator.flip_h = flipHor
	if animator.animation != anim_name:
		animator.play(anim_name)
