package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.config.UserSettings;
import com.cursedecho.constants.*;
import com.cursedecho.helpers.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;

public class GraphicsSettings extends VBox {

    private final ComboBox<String> resolutionDropdown;
    private final ComboBox<String> aspectRatioDropdown;
    private final CheckBox fullscreenCheckbox;
    private final Stage primaryStage;
    private final Main mainApp;

    public GraphicsSettings(Main mainApp) {
        this.mainApp = mainApp;
        this.primaryStage = mainApp.getPrimaryStage();
        MenuPosition.setPosition(this);

        // Settings Fields
        resolutionDropdown = CreateUI.createComboBox();
        aspectRatioDropdown = CreateUI.createComboBox();
        fullscreenCheckbox = CreateUI.createCheckbox(null);

        // Display Fields with Labels
        HBox resolutionDropdownBox = CreateUI.createLabeledControl("Resolution: ", resolutionDropdown);
        HBox aspectRatioDropdownBox = CreateUI.createLabeledControl("Aspect Ratio: ", aspectRatioDropdown);
        HBox fullscreenChkBox = CreateUI.createLabeledControl("Fullscreen: ", fullscreenCheckbox);

        // Resolution Dropdown
        updateResolutionDropdown(UserSettings.preferredAspectRatio);
        resolutionDropdown.setValue(UserSettings.screenWidth + "x" + UserSettings.screenHeight);

        // Aspect Ratio Dropdown
        aspectRatioDropdown.getItems().addAll(DisplayRatio.getAll());
        aspectRatioDropdown.setValue(UserSettings.preferredAspectRatio);
        aspectRatioDropdown.setOnAction(e -> updateResolutionDropdown(aspectRatioDropdown.getValue()));

        // Fullscreen Checkbox
        fullscreenCheckbox.setSelected(UserSettings.fullscreenEnabled);
        fullscreenCheckbox.setOnAction(e -> updateResolutionDropdown(aspectRatioDropdown.getValue()));

        // Buttons
        Button applyButton = CreateUI.createButton("Apply", this::applySettings);
        Button backButton = CreateUI.createButton("Back", mainApp::showSettingsScene);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox backOrApplyButtonBox = new HBox(backButton, spacer, applyButton);

        getChildren().addAll(resolutionDropdownBox, aspectRatioDropdownBox, fullscreenChkBox, backOrApplyButtonBox);
    }

    private void updateResolutionDropdown(String aspectRatio) {
        resolutionDropdown.getItems().clear();
        resolutionDropdown.getItems().addAll(DisplayResolution.getResolutions(aspectRatio));
        resolutionDropdown.setValue(resolutionDropdown.getItems().get(0));
    }

    // Method to save and apply the graphics settings
    private void applySettings() {
        // Parse resolution setting
        String[] resolution = resolutionDropdown.getValue().split("x");
        UserSettings.screenWidth = Integer.parseInt(resolution[0]);
        UserSettings.screenHeight = Integer.parseInt(resolution[1]);

        // Apply aspect ratio and fullscreen settings
        UserSettings.preferredAspectRatio = aspectRatioDropdown.getValue();
        UserSettings.fullscreenEnabled = fullscreenCheckbox.isSelected();

        // Update the background path based on the selected aspect ratio
        UserSettings.backgroundPath = selectBackgroundImagePath(UserSettings.preferredAspectRatio);

        // Save settings and apply changes
        UserSettings.saveSettings();

        if (UserSettings.fullscreenEnabled) {
            setFullscreen(UserSettings.screenWidth, UserSettings.screenHeight);
        } else {
            setWindowed(UserSettings.screenWidth, UserSettings.screenHeight);
        }

        mainApp.getRoot().toggleTitleBar(!UserSettings.fullscreenEnabled);
        BackgroundHelper.setBackground(mainApp.getRoot());
    }

    private String selectBackgroundImagePath(String aspectRatio) {
        String formattedRatio = aspectRatio.replace(":", "x");
        return String.format("/img/menu/menu-bg-%s.png", formattedRatio);
    }

    private void setFullscreen(int targetWidth, int targetHeight) {
        primaryStage.setFullScreen(true);
        double scaleX = targetWidth / DisplayUtils.getEffectiveScreenWidth();
        double scaleY = targetHeight / DisplayUtils.getEffectiveScreenHeight();
        primaryStage.setRenderScaleX(scaleX * Screen.getPrimary().getOutputScaleX());
        primaryStage.setRenderScaleY(scaleY * Screen.getPrimary().getOutputScaleY());
    }

    private void setWindowed(int targetWidth, int targetHeight) {
        primaryStage.setFullScreen(false);
        primaryStage.setWidth(targetWidth / Screen.getPrimary().getOutputScaleX());
        primaryStage.setHeight(targetHeight / Screen.getPrimary().getOutputScaleY());
        primaryStage.setRenderScaleX(Screen.getPrimary().getOutputScaleX());
        primaryStage.setRenderScaleY(Screen.getPrimary().getOutputScaleY());
        primaryStage.centerOnScreen();
    }
}
