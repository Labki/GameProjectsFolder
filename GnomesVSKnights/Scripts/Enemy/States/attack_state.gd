extends State

class_name AttackState

var PlayAnimation = Global.playAnimation.new()

func enter(_owner):
	state_owner = _owner
	state_owner.attack_timer = 0  # Reset the attack timer when entering the state

func update(delta):
	if (state_owner.health < state_owner.max_health / 5):
		pass
	if not state_owner.is_target_in_attack_range(state_owner.target) and not state_owner.is_attacking:
		state_owner.change_state(state_owner.chase_state)
	else:
		# Manage the attack cooldown and perform the attack if ready
		if state_owner.attack_timer > 0:
			state_owner.attack_timer -= delta
		
		if state_owner.attack_timer <= 0 and state_owner.target:
			perform_attack()
			state_owner.attack_timer = state_owner.attack_cooldown
		else:
			if (not state_owner.is_attacking) and state_owner.is_alive:
				PlayAnimation.play(state_owner.animator, "idle")

func perform_attack():
	state_owner.attack(state_owner.target)

func exit():
	pass
