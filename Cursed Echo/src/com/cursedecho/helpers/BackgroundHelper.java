package com.cursedecho.helpers;

import com.cursedecho.scenes.Game;
import com.cursedecho.config.UserSettings;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundHelper {

    /**
     * Sets the background image for a given Pane based on the path specified in UserSettings.
     * @param pane The Pane to apply the background to
     */
    public static void setBackground(Pane pane) {
        String imagePath;
        boolean repeatX = false;
        if (pane instanceof Game) {
            imagePath = "/assets/Forest/Preview/Background.png";
            repeatX = true;
        } else {
            imagePath = UserSettings.backgroundPath;
        }
        Image backgroundImage = new Image(BackgroundHelper.class.getResourceAsStream(imagePath));
        BackgroundImage stretchableBackground = new BackgroundImage(
                backgroundImage,
                repeatX ? BackgroundRepeat.REPEAT : BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        pane.setBackground(new Background(stretchableBackground));
    }
}
