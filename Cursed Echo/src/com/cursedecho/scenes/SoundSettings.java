package com.cursedecho.scenes;

import com.cursedecho.Main;
import com.cursedecho.config.UserSettings;
import com.cursedecho.helpers.CreateUI;
import com.cursedecho.helpers.MenuPosition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import javax.print.DocFlavor;

public class SoundSettings extends VBox {

    private final Slider musicVolumeSlider;
    private final Slider sfxVolumeSlider;
    private final CheckBox muteCheckbox;

    public SoundSettings(Main mainApp) {
        MenuPosition.setPosition(this);

        // Music Volume Slider
        musicVolumeSlider = CreateUI.createSlider(0, 100, UserSettings.musicVolume);
        sfxVolumeSlider = CreateUI.createSlider(0, 100, UserSettings.soundEffectsVolume);
        HBox musicSliderBox = CreateUI.createLabeledControl("Music Volume: ", musicVolumeSlider);
        HBox sfxSliderBox = CreateUI.createLabeledControl("SFX Volume: ", sfxVolumeSlider);

        // Mute Checkbox
        muteCheckbox = CreateUI.createCheckbox(null);
        HBox muteChkBox = CreateUI.createLabeledControl("Enable Sound: ", muteCheckbox);
        muteCheckbox.setSelected(UserSettings.soundEnabled);
        muteCheckbox.setOnAction(e -> toggleMute());
        if (!UserSettings.soundEnabled) {
            musicVolumeSlider.setDisable(true);
            sfxVolumeSlider.setDisable(true);
        }

        // Buttons
        Button applyButton = CreateUI.createButton("Apply", this::applySettings);
        Button backButton = CreateUI.createButton("Back", mainApp::showSettingsScene);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox backOrApplyButtonBox = new HBox(backButton, spacer, applyButton);

        getChildren().addAll(
                muteChkBox,
                musicSliderBox,
                sfxSliderBox,
                backOrApplyButtonBox
        );
    }

    // Method to apply and save sound settings
    private void applySettings() {
        UserSettings.musicVolume = muteCheckbox.isSelected() ? musicVolumeSlider.getValue() : 0;
        UserSettings.soundEffectsVolume = muteCheckbox.isSelected() ? sfxVolumeSlider.getValue() : 0;
        UserSettings.soundEnabled = muteCheckbox.isSelected();
        UserSettings.saveSettings();

        // Apply to actual audio system here if needed
        System.out.println("Sound settings applied: Music Volume - " + UserSettings.musicVolume +
                ", SFX Volume - " + UserSettings.soundEffectsVolume);
    }

    // Toggles mute and disables sliders if muted
    private void toggleMute() {
        boolean isEnabled = !muteCheckbox.isSelected();
        musicVolumeSlider.setDisable(isEnabled);
        sfxVolumeSlider.setDisable(isEnabled);
    }
}
