package com.cursedecho.helpers;

import javafx.stage.Screen;

public class DisplayUtils {
    public static double getEffectiveScreenWidth() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double scalingFactor = Screen.getPrimary().getOutputScaleX();
        return screenWidth * scalingFactor;
    }

    public static double getEffectiveScreenHeight() {
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double scalingFactor = Screen.getPrimary().getOutputScaleY();
        return screenHeight * scalingFactor;
    }
}
