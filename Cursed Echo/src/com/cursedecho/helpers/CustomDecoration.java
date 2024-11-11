package com.cursedecho.helpers;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomDecoration extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;
    private HBox titleBar;
    private final Stage primaryStage;

    public CustomDecoration(Stage primaryStage, boolean showTitleBar) {
        this.primaryStage = primaryStage;
        titleBar = createTitleBar(primaryStage);

        if (showTitleBar) {
            setTop(titleBar);
            addWindowDragSupport(primaryStage, titleBar);
        }
    }

    public void toggleTitleBar(boolean show) {
        if (show) {
            setTop(titleBar);
            addWindowDragSupport(primaryStage, titleBar);
        } else {
            setTop(null);
        }
    }

    private HBox createTitleBar(Stage primaryStage) {
        HBox titleBar = new HBox();
        Button minimizeButton = new Button("_");
        Button closeButton = new Button("X");

        titleBar.getStyleClass().add("title-bar");
        minimizeButton.getStyleClass().add("title-bar-button");
        closeButton.getStyleClass().add("title-bar-button");

        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));
        closeButton.setOnAction(e -> primaryStage.close());

        titleBar.getChildren().addAll(minimizeButton, closeButton);
        return titleBar;
    }

    private void addWindowDragSupport(Stage primaryStage, HBox titleBar) {
        titleBar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }
}
