package com.cursedecho.characters;

import javafx.scene.Group;
import javafx.scene.image.*;
import java.util.*;

public abstract class BaseCharacter extends Group {
    protected int health;
    protected double speed;
    protected int attackPower;
    protected double attackSpeed;
    protected double attackRange;
    protected int armor;
    protected boolean dead = false;

    // Protected Animation fields
    protected ImageView characterView;
    protected List<Image> currentFrames;
    protected int currentFrame = 0;
    protected long lastFrameTime = 0;
    protected static final long FRAME_DURATION = 69_000_000; // 69 ms per frame

    protected boolean isAttacking = false;
    protected long lastAttackTime = 0;

    // Gravity
    private static final double TERMINAL_VELOCITY = 1.0;
    protected double velocityY = 0; // Vertical velocity
    protected static final double GRAVITY = 0.98; // Gravity constant
    protected boolean onGround = false;

    public BaseCharacter(int width, int height, int health, double speed, int attackPower, double attackSpeed, double attackRange,  int armor) {
        // Base fields
        this.health = health;
        this.speed = speed;
        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.armor = armor;

        // Animation fields
        characterView = new ImageView();
        characterView.setFitWidth(width);
        characterView.setFitHeight(height);
        getChildren().add(characterView);
    }

    protected void loadFrames(List<Image> frameList, String stripPath, int frameCount) {
        Image stripImage = new Image(getClass().getResourceAsStream(stripPath));
        int frameWidth = (int) stripImage.getWidth() / frameCount;

        for (int i = 0; i < frameCount; i++) {
            WritableImage frame = new WritableImage(stripImage.getPixelReader(), i * frameWidth, 0, frameWidth, (int) stripImage.getHeight());
            frameList.add(frame);
        }
    }

    public void setAnimation(List<Image> frames) {
        if (currentFrames != frames) {
            currentFrames = frames;
            currentFrame = 0;
        }
    }

    public void animate() {
        if (System.nanoTime() - lastFrameTime >= FRAME_DURATION) {
            lastFrameTime = System.nanoTime();
            currentFrame = (currentFrame + 1) % currentFrames.size();
            characterView.setImage(currentFrames.get(currentFrame));
        }
    }

    public void performAttack(BaseCharacter target) {
        long currentTime = System.currentTimeMillis();
        long attackCooldown = (long) (1000 / attackSpeed);

        if (!isAttacking && currentTime - lastAttackTime >= attackCooldown) {
            isAttacking = true;
            setAnimation(getAttackFrames());
            currentFrame = 0;
            lastAttackTime = currentTime;
        }
        if (isAttacking) {
            animate();
            if (currentFrame == getAttackFrames().size() - 1) {
                isAttacking = false;
                if (target != null) {
                    target.takeDamage(attackPower);
                }
            }
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void move(double dx, double dy) {
        if (!onGround) {
            applyGravity();
        }
        setTranslateX(getTranslateX() + dx * speed);
        animate();
    }

    public void applyGravity() {
        if (!isOnGround()) {
            velocityY += GRAVITY * 0.5;
            velocityY = Math.min(velocityY, TERMINAL_VELOCITY);
            setTranslateY(getTranslateY() + velocityY);
        }
    }

    public void stopVerticalMovement() {
        velocityY = 0;
    }

    public void takeDamage(double amount) {
        double damageTaken = amount * (1 - ((double) armor / 100));
        health -= (int) damageTaken;
        if (health <= 0) {
            onDeath();
        }
    }

    protected void onDeath() {
        dead = true;
        this.setVisible(false);
    }

    public double getAttackRange() {
        return attackRange;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public boolean isOnGround() {
        return onGround;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }



    public void onCollisionWith(BaseCharacter other) {
    }
    public boolean isAlive() {
        return health > 0;
    }
    public boolean isDead() {
        return dead;
    }


    protected abstract List<Image> getAttackFrames();
}
