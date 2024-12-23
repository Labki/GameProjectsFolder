package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.config.UserSettings;
import com.cursedecho.constants.*;
import com.cursedecho.helpers.*;
import com.cursedecho.utils.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GraphicsSettings extends VBox {

    private final ComboBox<String> resolutionDropdown;
    private final CheckBox fullscreenCheckbox;
    private final Main mainApp;

    public GraphicsSettings(Main mainApp) {
        this.mainApp = mainApp;
        MenuPosition.setPosition(this);

        // Settings Fields
        resolutionDropdown = CreateUI.createComboBox();
        fullscreenCheckbox = CreateUI.createCheckbox(null);

        // Display Fields with Labels
        HBox resolutionDropdownBox = CreateUI.createLabeledControl("Resolution: ", resolutionDropdown);
        HBox fullscreenChkBox = CreateUI.createLabeledControl("Fullscreen: ", fullscreenCheckbox);

        // Resolution Dropdown
        resolutionDropdown.setValue(UserSettings.screenWidth + "x" + UserSettings.screenHeight);

        // Fullscreen Checkbox
        fullscreenCheckbox.setSelected(UserSettings.fullscreenEnabled);
        fullscreenCheckbox.setOnAction(e -> handleFullscreenToggle());

        // Buttons
        Button applyButton = CreateUI.createButton("Apply", this::applySettings);
        Button backButton = CreateUI.createButton("Back", mainApp::showSettingsScene);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox backOrApplyButtonBox = new HBox(backButton, spacer, applyButton);

        getChildren().addAll(resolutionDropdownBox, fullscreenChkBox, backOrApplyButtonBox);

        updateResolutionDropdown();
        handleFullscreenToggle();
    }

    private void handleFullscreenToggle() {
        boolean isFullscreen = fullscreenCheckbox.isSelected();
        resolutionDropdown.setDisable(isFullscreen);
        if (isFullscreen) {
            UserSettings.screenWidth = (int) DisplayUtils.getEffectiveScreenWidth();
            UserSettings.screenHeight = (int) DisplayUtils.getEffectiveScreenHeight();
        } else {
            updateResolutionDropdown();
        }
    }

    private void updateResolutionDropdown() {
        double screenWidth = DisplayUtils.getEffectiveScreenWidth();
        double screenHeight = DisplayUtils.getEffectiveScreenHeight();
        String aspectRatio = GetAspectRatio.calculate(screenWidth, screenHeight);
        resolutionDropdown.getItems().clear();
        resolutionDropdown.getItems().addAll(DisplayResolution.getResolutions(aspectRatio));
        resolutionDropdown.setValue(UserSettings.screenWidth + "x" + UserSettings.screenHeight);
    }

    // Method to save and apply the graphics settings
    private void applySettings() {
        String[] resolution = resolutionDropdown.getValue().split("x");
        UserSettings.screenWidth = Integer.parseInt(resolution[0]);
        UserSettings.screenHeight = Integer.parseInt(resolution[1]);
        UserSettings.fullscreenEnabled = fullscreenCheckbox.isSelected();
        UserSettings.saveSettings();
        if (UserSettings.fullscreenEnabled) {
            mainApp.getPrimaryStage().setFullScreen(true);
        } else {
            mainApp.getPrimaryStage().setFullScreen(false);
            mainApp.getPrimaryStage().setWidth(UserSettings.screenWidth);
            mainApp.getPrimaryStage().setHeight(UserSettings.screenHeight);
            mainApp.getPrimaryStage().centerOnScreen();
        }

        mainApp.getRoot().toggleTitleBar(!UserSettings.fullscreenEnabled);
        BackgroundHelper.setBackground(mainApp.getRoot());
    }
}
