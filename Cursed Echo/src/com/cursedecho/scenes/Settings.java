package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.helpers.CreateUI;
import com.cursedecho.helpers.MenuPosition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Settings extends VBox {

    public Settings(Main mainApp) {
        MenuPosition.setPosition(this);

        // Buttons for different settings sections
        Button graphicsButton = CreateUI.createButton("Graphics Settings", mainApp::showGraphicsSettings);
        Button soundButton = CreateUI.createButton("Sound Settings", mainApp::showSoundSettings);
        Button backButton = CreateUI.createButton("Back", mainApp::showMainMenu);

        getChildren().addAll(graphicsButton, soundButton, backButton);
    }
}
