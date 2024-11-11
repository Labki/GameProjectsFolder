package com.cursedecho.characters;

import javafx.scene.image.Image;
import java.util.List;

public abstract class Enemy extends BaseCharacter {
    public enum State { IDLE, CHASE, ATTACK, DEAD }
    protected State state = State.IDLE;
    protected Player player;

    public Enemy(int width, int height, int health, double speed, int attackPower, double attackSpeed, double attackRange, int armor, Player player) {
        super(width, height, health, speed, attackPower, attackSpeed, attackRange, armor );
        this.player = player;
    }

    public void attackPlayer() {
        performAttack(player);
    }

    public void update() {
        switch (state) {
            case IDLE:
                idleBehavior();
                break;
            case CHASE:
                if (isPlayerInRange()) {
                    state = State.ATTACK;
                } else {
                    chasePlayer();
                }
                break;
            case ATTACK:
                if (isPlayerInRange()) {
                    attackPlayer();
                } else {
                    state = State.CHASE;
                }
                break;
            case DEAD:
                onDeath();
                break;
        }
    }

    private boolean canMove() {
        return state != State.ATTACK;
    }

    protected void idleBehavior() {
        double distance = Math.hypot(player.getTranslateX() - getTranslateX(), player.getTranslateY() - getTranslateY());
        if (distance < 200) {
            state = State.CHASE;
        }
        move(0, 0);
        setAnimation(getIdleFrames());
    }

    protected void chasePlayer() {
        double dx = player.getTranslateX() - getTranslateX();
        double dy = player.getTranslateY() - getTranslateY();
        double distance = Math.hypot(dx, dy);

        if (distance > 10) {
            dx /= distance;
            dy /= distance;
            move(dx, dy);
        } else {
            state = State.ATTACK;
        }
        setAnimation(getMoveFrames());
    }

    private boolean isPlayerInRange() {
        double distance = Math.hypot(player.getTranslateX() - getTranslateX(), player.getTranslateY() - getTranslateY());
        return distance <= 10;
    }

    protected abstract List<Image> getIdleFrames();
    protected abstract List<Image> getMoveFrames();
    protected abstract List<Image> getAttackFrames();
//    protected abstract List<Image> getDeathFrames();
}
