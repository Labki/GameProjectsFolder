package com.cursedecho;

import com.cursedecho.config.UserSettings;
import com.cursedecho.scenes.*;
import com.cursedecho.helpers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage primaryStage;
    private CustomDecoration root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        UserSettings.loadSettings();

        primaryStage.initStyle(StageStyle.UNDECORATED);
        root = new CustomDecoration(primaryStage, !UserSettings.fullscreenEnabled);
        scene = new Scene(root, UserSettings.screenWidth, UserSettings.screenHeight);

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Font.loadFont(getClass().getResourceAsStream("/fonts/OldLondon.ttf"), 24);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 24);

        primaryStage.setTitle("Cursed Echo");
        primaryStage.setScene(scene);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCodeCombination.keyCombination("F11"));
        primaryStage.setResizable(false);

        primaryStage.show();
        primaryStage.setFullScreen(UserSettings.fullscreenEnabled);
        showMainMenu();
    }

    private void setRoot(Pane newRoot) {
        root.setCenter(newRoot);
    }
    public CustomDecoration getRoot() {
        return root;
    }

    public void showMainMenu() {
        // Main menu layout
        VBox menuLayout = new VBox(20);
        Button startButton = CreateUI.createButton("Start Game", this::showGameScene);
        Button settingsButton = CreateUI.createButton("Settings", this::showSettingsScene);
        Button quitButton = CreateUI.createButton("Quit", primaryStage::close);

        startButton.setOnAction(e -> showGameScene());
        settingsButton.setOnAction(e -> showSettingsScene());
        quitButton.setOnAction(e -> primaryStage.close());

        menuLayout.getChildren().addAll(startButton, settingsButton, quitButton);
        MenuPosition.setPosition(menuLayout);

        BackgroundHelper.setBackground(root);
        setRoot(menuLayout);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showGameScene() {
        setRoot(new Game(this));
    }
    public void showSettingsScene() {
        setRoot(new Settings(this));
    }
    public void showGraphicsSettings() {
        setRoot(new GraphicsSettings(this));
    }
    public void showSoundSettings() {
        setRoot(new SoundSettings(this));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
