package com.cursedecho.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserSettings {

    // Default screen settings
    public static final int DEFAULT_SCREEN_WIDTH = 1920;
    public static final int DEFAULT_SCREEN_HEIGHT = 1080;
    public static int screenWidth = DEFAULT_SCREEN_WIDTH;
    public static int screenHeight = DEFAULT_SCREEN_HEIGHT;
    public static String preferredAspectRatio = "16:9";
    public static boolean fullscreenEnabled = true;

    // Background settings
    public static String backgroundPath = "/img/menu/menu-bg-16x9.png";

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
            screenWidth = Integer.parseInt(props.getProperty("screenWidth", String.valueOf(DEFAULT_SCREEN_WIDTH)));
            screenHeight = Integer.parseInt(props.getProperty("screenHeight", String.valueOf(DEFAULT_SCREEN_HEIGHT)));
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
