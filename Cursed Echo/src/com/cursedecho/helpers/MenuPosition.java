package com.cursedecho.helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class MenuPosition {

    private static final double DEFAULT_SPACING = 15;
    private static final Insets DEFAULT_PADDING = new Insets(100);
    private static final Pos DEFAULT_ALIGNMENT = Pos.BOTTOM_LEFT;

    public static void setPosition(Pane pane) {
        setPosition(pane, DEFAULT_SPACING, DEFAULT_PADDING, DEFAULT_ALIGNMENT);
    }

    public static void setPosition(Pane pane, double spacing, Insets padding, Pos alignment) {
        if (pane instanceof HBox hbox) {
            hbox.setSpacing(spacing);
            hbox.setPadding(padding);
            hbox.setAlignment(alignment);
        } else if (pane instanceof VBox vbox) {
            vbox.setSpacing(spacing);
            vbox.setPadding(padding);
            vbox.setAlignment(alignment);
        } else {
            pane.setPadding(padding);
        }
    }
}
