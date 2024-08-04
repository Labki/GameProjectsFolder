extends Node

func play(animator, current_dir, movement, flipHor):
	var anim_name = current_dir + "_" + movement
	if movement == "walk":
		animator.flip_h = flipHor
	if animator.animation != anim_name:
		animator.play(anim_name)
