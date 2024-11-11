package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.characters.*;
import com.cursedecho.characters.enemies.*;
import com.cursedecho.helpers.BackgroundHelper;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import java.util.*;

public class Game extends Pane {
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private AnimationTimer gameLoop;
    private Set<KeyCode> activeKeys = new HashSet<>();

    public Game(Main mainApp) {
        setupGameObjects();
        setupInputHandlers();
        startGameLoop();
    }

    private void setupGameObjects() {
        BackgroundHelper.setBackground(this);

        player = new Player(128,128,100,5,20,1.5,50,10);
        player.setTranslateX(100);
        player.setTranslateY(700);
        this.getChildren().add(player);

        // Initialize enemies with positions
        addEnemy(new Bat(player), 600, 700);
        addEnemy(new Wolf(player), 750, 700);
        addEnemy(new Golem(player), 950, 700);
        addEnemy(new Witch(player), 1100, 700);
    }

    private void addEnemy(Enemy enemy, double x, double y) {
        enemy.setTranslateX(x);
        enemy.setTranslateY(y);
        enemies.add(enemy);
        this.getChildren().add(enemy);
    }

    private void setupInputHandlers() {
        this.setFocusTraversable(true);
        setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
            if (event.getCode() == KeyCode.SPACE) {
                if (!player.isAttacking()) {
                    player.attackEnemy(enemies);
                }
            }
        });
        setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        handlePlayerMovement();
        player.updateAttackAnimation(enemies);
        handleEnemyUpdates();
        handleCollisions();
    }

    private void handlePlayerMovement() {
        if (player.isAttacking()) {
            return;
        }
        double moveX = 0;
        double moveY = 0;

//        if (activeKeys.contains(KeyCode.W)) moveY -= 1;
//        if (activeKeys.contains(KeyCode.S)) moveY += 1;
        if (activeKeys.contains(KeyCode.A)) moveX -= 1;
        if (activeKeys.contains(KeyCode.D)) moveX += 1;

        double length = Math.hypot(moveX, moveY);
        if (length != 0) {
            moveX /= length;
            moveY /= length;
        }
        player.move(moveX, moveY);
    }

    private void handleEnemyUpdates() {
        for (Enemy enemy : enemies) {
            double distanceToPlayer = Math.hypot(player.getTranslateX() - enemy.getTranslateX(),
                    player.getTranslateY() - enemy.getTranslateY());

            if (distanceToPlayer <= enemy.getAttackRange()) {
                enemy.attackPlayer();
            } else {
                enemy.update();
            }
        }
    }

    private void handleCollisions() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();

            // Check player-enemy collision
            if (isColliding(player, enemy)) {
                player.onCollisionWith(enemy);
                if (player.isDead()) {
                    System.out.println("Player has been killed!");
                    gameLoop.stop();
                    return;
                }
            }

            // Check enemy-enemy collisions
            for (Enemy otherEnemy : enemies) {
                if (enemy != otherEnemy && isColliding(enemy, otherEnemy)) {
                    enemy.onCollisionWith(otherEnemy);
                    if (enemy.isDead()) {
                        iterator.remove();
                        this.getChildren().remove(enemy);
                        break;
                    }
                }
            }
        }
    }

    private boolean isColliding(BaseCharacter character1, BaseCharacter character2) {
        Bounds bounds1 = character1.getBoundsInParent();
        Bounds bounds2 = character2.getBoundsInParent();
        return bounds1.intersects(bounds2);
    }
}
