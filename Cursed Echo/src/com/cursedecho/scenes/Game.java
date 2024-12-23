package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.characters.*;
import com.cursedecho.characters.enemies.*;
import com.cursedecho.config.UserSettings;
import com.cursedecho.helpers.BackgroundHelper;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.*;
import java.util.List;

public class Game extends Pane {
    private Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private AnimationTimer gameLoop;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private boolean isPaused = false;
    private PauseMenu pauseMenu;
    private Rectangle platform;

    public Game(Main mainApp) {
        setupGameObjects();
        setupInputHandlers();
        startGameLoop();
    }

    private void setupGameObjects() {
        BackgroundHelper.setBackground(this);
        addPlatform();

        player = new Player(128,128,100,1,20,1.5,50,10);
        player.setTranslateX(100);
        player.setTranslateY(platform.getTranslateY() - player.getBoundsInParent().getHeight() - 10);
        this.getChildren().add(player);

        addEnemy(new Bat(player), 600, platform.getTranslateY() - new Bat(player).getBoundsInParent().getHeight());
        addEnemy(new Wolf(player), 750, platform.getTranslateY() - new Wolf(player).getBoundsInParent().getHeight());
        addEnemy(new Golem(player), 950, platform.getTranslateY() - new Golem(player).getBoundsInParent().getHeight());
        addEnemy(new Witch(player), 1100, platform.getTranslateY() - new Witch(player).getBoundsInParent().getHeight());

    }

    private void addPlatform() {
        double platformWidth = UserSettings.screenWidth * 1.5;
        double platformHeight = UserSettings.screenHeight * 0.2;
        double platformX = (UserSettings.screenWidth - platformWidth) / 2;
        double platformY = UserSettings.screenHeight - (UserSettings.screenHeight * 0.1);

        platform = new Rectangle(platformWidth, platformHeight);
        platform.setTranslateX(platformX);
        platform.setTranslateY(platformY);
        platform.setFill(Color.RED);

        getChildren().add(platform);
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
                player.jump();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                togglePause();
            }
        });
        setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> {
                    if (!player.isAttacking()) {
                        player.attackEnemy(enemies);
                    }
                }
                case SECONDARY -> {
                    System.out.println("Right click action triggered!");
                }
                default -> System.out.println("Other mouse button clicked!");
            }
        });
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!Game.this.isFocused()) {
                    Game.this.requestFocus();
                }
                if (!isPaused) {
                    updateGame();
                }
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        for (var node : this.getChildren()) {
            if (node instanceof BaseCharacter character) {
                character.applyGravity();
                handlePlatformCollisions(character);
            }
        }
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
            if (!enemy.isOnGround()) {
                continue;
            }
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

    private void handlePlatformCollisions(BaseCharacter character) {
        Bounds characterBounds = character.getBoundsInParent();
        Bounds platformBounds = platform.getBoundsInParent();
        if (characterBounds.intersects(platformBounds)) {
            character.setTranslateY(platform.getTranslateY() - characterBounds.getHeight());
            character.stopVerticalMovement();
            character.setOnGround(true);
        } else {
            character.setOnGround(false);
        }
    }


    private void togglePause() {
        if (isPaused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    private void pauseGame() {
        isPaused = true;
        gameLoop.stop();

        if (pauseMenu == null) {
            pauseMenu = new PauseMenu(this,
                    this::resumeGame,
                    this::quitGame
            );
            this.getChildren().add(pauseMenu);
        }
    }

    private void resumeGame() {
        isPaused = false;
        gameLoop.start();
        this.getChildren().remove(pauseMenu);
        pauseMenu = null;
    }

    private void quitGame() {
        System.out.println("Quitting game...");
        System.exit(0);
    }

}
