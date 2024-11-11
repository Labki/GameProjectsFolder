package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.config.UserSettings;
import com.cursedecho.constants.*;
import com.cursedecho.helpers.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

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
        HBox aspectRatioDropdownBox = CreateUI.createLabeledControl("AspectRatio: ", aspectRatioDropdown);
        HBox fullscreenChkBox = CreateUI.createLabeledControl("Fullscreen: ", fullscreenCheckbox);

        // Resolution Dropdown
        updateResolutionDropdown(UserSettings.preferredAspectRatio, UserSettings.fullscreenEnabled);
        resolutionDropdown.setValue(UserSettings.screenWidth + "x" + UserSettings.screenHeight);

        // Aspect Ratio Dropdown
        aspectRatioDropdown.getItems().addAll(DisplayRatio.getAll());
        aspectRatioDropdown.setValue(UserSettings.preferredAspectRatio);
        aspectRatioDropdown.setOnAction(e -> updateResolutionDropdown(aspectRatioDropdown.getValue(), fullscreenCheckbox.isSelected()));

        // Fullscreen Checkbox
        fullscreenCheckbox.setSelected(UserSettings.fullscreenEnabled);
        fullscreenCheckbox.setOnAction(e -> updateResolutionDropdown(aspectRatioDropdown.getValue(), fullscreenCheckbox.isSelected()));

        // Buttons
        Button applyButton = CreateUI.createButton("Apply", this::applySettings);
        Button backButton = CreateUI.createButton("Back", mainApp::showSettingsScene);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox backOrApplyButtonBox = new HBox(backButton, spacer, applyButton);

        getChildren().addAll(resolutionDropdownBox, aspectRatioDropdownBox, fullscreenChkBox, backOrApplyButtonBox);
    }

    private void updateResolutionDropdown(String aspectRatio, Boolean fullscreen) {
        resolutionDropdown.getItems().clear();
        resolutionDropdown.getItems().addAll(DisplayResolution.getResolutions(aspectRatio, fullscreen));
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
            setFullscreen();
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

    private void setFullscreen() {
        primaryStage.setFullScreen(true);
        if (Objects.equals(UserSettings.preferredAspectRatio, DisplayRatio.RATIO_4_3)) {
            mainApp.getRoot().setScaleX(1.35);
        } else if (Objects.equals(UserSettings.preferredAspectRatio, DisplayRatio.RATIO_16_10)) {
            mainApp.getRoot().setScaleX(1.15);
        } else {
            mainApp.getRoot().setScaleX(1.0);
        }
    }

    private void setWindowed(int targetWidth, int targetHeight) {
        primaryStage.setFullScreen(false);
        mainApp.getRoot().setScaleX(1.0);
        mainApp.getRoot().setScaleY(1.0);
        primaryStage.setWidth(targetWidth);
        primaryStage.setHeight(targetHeight);
    }

}
