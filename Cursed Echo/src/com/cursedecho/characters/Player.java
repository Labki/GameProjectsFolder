package com.cursedecho.characters;

import javafx.scene.image.*;
import java.util.*;

public class Player extends BaseCharacter {
    // Animations
    private final List<Image> idleFrames = new ArrayList<>();
    private final List<Image> runFrames = new ArrayList<>();
    private final List<Image> attackFrames = new ArrayList<>();

    public Player(int width, int height, int health, double speed, int attackPower, double attackSpeed, double attackRange, int armor) {
        super(width, height, health, speed, attackPower, attackSpeed, attackRange, armor);

        loadFrames(idleFrames, "/assets/Knight/KnightIdle_strip.png", 15);
        loadFrames(runFrames, "/assets/Knight/KnightMove_strip.png", 8);
        loadFrames(attackFrames, "/assets/Knight/KnightAttack_strip.png", 22);
        setAnimation(idleFrames);
    }

    @Override
    protected List<Image> getAttackFrames() {
        return attackFrames;
    }

    public void attackEnemy(List<Enemy> enemies) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            double distance = Math.hypot(getTranslateX() - enemy.getTranslateX(),
                    getTranslateY() - enemy.getTranslateY());
            if (distance <= getAttackRange() && !isAttacking()) {
                System.out.println(distance + " " + enemy);
                performAttack(enemy);
                if (enemy.isDead()) {
                    System.out.println("Enemy killed: " + enemy);
                    iterator.remove();
                }
            }
        }
    }


    public void updateAttackAnimation(List<Enemy> enemies) {
        if (isAttacking()) {
            animate();
            if (getCurrentFrame() == getAttackFrames().size() - 1) {
                setAttacking(false);
                for (Enemy enemy : enemies) {
                    double distance = Math.hypot(getTranslateX() - enemy.getTranslateX(),
                            getTranslateY() - enemy.getTranslateY());
                    if (distance <= getAttackRange() && !isAttacking()) {
                        enemy.takeDamage(attackPower);
                    }
                }
            }
        }
    }


    @Override
    public void move(double dx, double dy) {
        if (!isAttacking) {
            setAnimation((dx != 0 || dy != 0) ? runFrames : idleFrames);
        }
        super.move(dx, dy);
    }

    public void jump() {
        if (onGround) {
            velocityY = -10;
            onGround = false;
        }
    }
}
