class_name AttackState
extends State

var PlayAnimation = Global.playAnimation.new()

func enter(_owner):
	state_owner = _owner
	state_owner.attack_timer = 0  # Reset the attack timer when entering the state

func update(delta):
	var player_position = state_owner.player.position

	if state_owner.target == null and not state_owner.is_attacking:
		state_owner.change_state(state_owner.chase_state)
	else:
		# Manage the attack cooldown and perform the attack if ready
		if state_owner.attack_timer > 0:
			if state_owner.preventAnimation == false:
				PlayAnimation.play(state_owner.animator, "idle")
			state_owner.attack_timer -= delta
		
		if state_owner.attack_timer <= 0 and state_owner.target:
			perform_attack()
			state_owner.attack_timer = state_owner.attack_cooldown

func perform_attack():
	state_owner.attack(state_owner.player)

func exit():
	pass
