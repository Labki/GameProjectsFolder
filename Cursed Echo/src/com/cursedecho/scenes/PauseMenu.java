package com.cursedecho.scenes;

import com.cursedecho.helpers.CreateUI;
import com.cursedecho.helpers.MenuPosition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PauseMenu extends Pane {
    public PauseMenu(Pane gamePane, Runnable onResume, Runnable onQuit) {

        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        setPrefSize(gamePane.getWidth(), gamePane.getHeight());

        Text pauseText = new Text("Game Paused");
        pauseText.setStyle("-fx-font-size: 36px; -fx-fill: white;");
        pauseText.setTranslateX(gamePane.getWidth() / 2 - 100); // Centered
        pauseText.setTranslateY(150);

        VBox vBox = new VBox();
        vBox.setPrefWidth(gamePane.getWidth());
        vBox.setPrefHeight(gamePane.getHeight());
        MenuPosition.setPosition(vBox);

        Button resumeButton = CreateUI.createButton("Resume", onResume);
        Button quitButton = CreateUI.createButton("Quit", onQuit);

        vBox.getChildren().addAll(resumeButton, quitButton);
        getChildren().addAll(pauseText, vBox);
    }
}
