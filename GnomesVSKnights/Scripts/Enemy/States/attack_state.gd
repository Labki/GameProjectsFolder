class_name AttackState
extends State

var PlayAnimation = Global.playAnimation.new()

func enter(_owner):
	state_owner = _owner
	state_owner.character.attack_timer = 0  # Reset the attack timer when entering the state

func update(delta):
	var character = state_owner.character
	var player_position = state_owner.player.position
	var enemy_position = state_owner.position + character.position

	if state_owner.target == null and not character.is_attacking:
		state_owner.change_state(state_owner.chase_state)
	else:
		# Manage the attack cooldown and perform the attack if ready
		if character.attack_timer > 0:
			if character.preventAnimation == false:
				PlayAnimation.play(character.animator, "idle")
			character.attack_timer -= delta
		
		if character.attack_timer <= 0 and state_owner.target:
			perform_attack()
			character.attack_timer = character.attack_cooldown

func perform_attack():
	state_owner.character.attack(state_owner.player)

func exit():
	pass
