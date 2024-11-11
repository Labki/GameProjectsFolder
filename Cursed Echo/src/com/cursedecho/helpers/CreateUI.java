package com.cursedecho.helpers;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

public class CreateUI {

    public static HBox createLabeledControl(String labelText, Control control) {
        Label label = new Label(labelText);
        label.getStyleClass().add("custom-label");

        HBox labelBox = new HBox(label);
        labelBox.setMaxWidth(500);
        labelBox.setAlignment(Pos.CENTER);

        HBox controlBox = new HBox(control);
        controlBox.setMaxWidth(300);
        controlBox.setAlignment(Pos.CENTER);

        HBox labeledControlBox = new HBox(10, labelBox, controlBox);
        labeledControlBox.setAlignment(Pos.BASELINE_LEFT);
        labeledControlBox.setMaxWidth(1000);
        return labeledControlBox;
    }

    public static Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setOnAction(e -> action.run());
        return button;
    }

    public static ComboBox<String> createComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getStyleClass().add("custom-combo-box");
        comboBox.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                comboBox.getStyleClass().add("expanded");
            } else {
                comboBox.getStyleClass().remove("expanded");
            }
        });
        comboBox.setPromptText("Select an option");
        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);
                            getStyleClass().add("custom-combo-box-item");
                        }
                    }
                };
            }
        });

        return comboBox;
    }

    public static CheckBox createCheckbox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.getStyleClass().add("custom-checkbox");
        return checkBox;
    }

    public static Slider createSlider(double min, double max, double initialValue) {
        Slider slider = new Slider(min, max, initialValue);
        slider.getStyleClass().add("custom-slider");
        return slider;
    }


}
