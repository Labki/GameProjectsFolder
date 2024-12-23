package com.cursedecho.config;

import com.cursedecho.helpers.DisplayUtils;
import com.cursedecho.utils.GetAspectRatio;
import javafx.stage.Screen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserSettings {

    // Default screen settings
    public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    public static String preferredAspectRatio = GetAspectRatio.calculate(screenWidth, screenHeight);
    public static boolean fullscreenEnabled = true;

    // Background settings
    private static String selectBackgroundImagePath(String aspectRatio) {
        String formattedRatio = aspectRatio.replace(":", "x");
        return String.format("/img/menu/menu-bg-%s.png", formattedRatio);
    }
    public static String backgroundPath = selectBackgroundImagePath(preferredAspectRatio);

    // Default audio settings
    public static boolean soundEnabled = true;
    public static double musicVolume = 0.5;
    public static double soundEffectsVolume = 0.5;

    // Settings file path
    private static final String SETTINGS_FILE = "user-settings.properties";

    private UserSettings() {}

    // Load settings from the properties file
    public static void loadSettings() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(SETTINGS_FILE)) {
            props.load(input);

            // Load screen settings
            screenWidth = Integer.parseInt(props.getProperty("screenWidth", String.valueOf(screenWidth)));
            screenHeight = Integer.parseInt(props.getProperty("screenHeight", String.valueOf(screenHeight)));
            preferredAspectRatio = props.getProperty("preferredAspectRatio", String.valueOf(preferredAspectRatio));
            fullscreenEnabled = Boolean.parseBoolean(props.getProperty("fullscreenEnabled", String.valueOf(fullscreenEnabled)));

            // Load background setting
            backgroundPath = props.getProperty("backgroundPath", backgroundPath);

            // Load audio settings
            soundEnabled = Boolean.parseBoolean(props.getProperty("soundEnabled", String.valueOf(soundEnabled)));
            musicVolume = Double.parseDouble(props.getProperty("musicVolume", String.valueOf(musicVolume)));
            soundEffectsVolume = Double.parseDouble(props.getProperty("soundEffectsVolume", String.valueOf(soundEffectsVolume)));

        } catch (IOException e) {
            System.out.println("Settings file not found, using default settings.");
        }
    }

    // Save settings to the properties file
    public static void saveSettings() {
        Properties props = new Properties();

        // Screen settings
        props.setProperty("screenWidth", String.valueOf(screenWidth));
        props.setProperty("screenHeight", String.valueOf(screenHeight));
        props.setProperty("preferredAspectRatio", preferredAspectRatio);
        props.setProperty("fullscreenEnabled", String.valueOf(fullscreenEnabled));

        // Background setting
        props.setProperty("backgroundPath", backgroundPath);

        // Audio settings
        props.setProperty("soundEnabled", String.valueOf(soundEnabled));
        props.setProperty("musicVolume", String.valueOf(musicVolume));
        props.setProperty("soundEffectsVolume", String.valueOf(soundEffectsVolume));

        try (FileOutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            props.store(output, "User Settings");
        } catch (IOException e) {
            System.out.println("Could not save settings.");
        }
    }
}
