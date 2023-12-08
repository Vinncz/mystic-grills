package views.components.radio_buttons;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import views.components.hboxes.BaseHBox;
import views.components.labels.H5Label;

import java.util.ArrayList;

public class BaseRadioButtonGroupQuickDraw {

    private Pane layoutProvider;
    private ArrayList<RadioButtonData> data = new ArrayList<>();
    private ArrayList<HBox> buttonContainer = new ArrayList<>();
    private ToggleGroup pool = new ToggleGroup();

    public BaseRadioButtonGroupQuickDraw (Pane _layout, ArrayList<RadioButtonData> _listOfButtonData) {
        this.layoutProvider = _layout;
        data = _listOfButtonData;

        for (RadioButtonData _d : data) {
            RadioButton rb = new RadioButton();
            rb.setUserData(_d.getSupportingData());
            rb.setToggleGroup(pool);
            rb.setVisible(false);
            rb.setManaged(false);

            HBox buttonWrapper = new BaseHBox().withLooseSpacing().centerContentBothAxis();
            Label l = new H5Label(_d.getDisplayText());
            l.getStyleClass().setAll("cursorPointer", "radioButton");

            rb.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    l.getStyleClass().setAll("cursorPointer", "radioButtonSelected");
                } else {
                    l.getStyleClass().setAll("cursorPointer", "radioButton");
                }
            });

            buttonWrapper.setOnMouseClicked(e -> rb.fire());
            buttonWrapper.getChildren().addAll(rb, l);
            buttonContainer.add(buttonWrapper);
        }

        this.layoutProvider.getChildren().addAll(buttonContainer);
    }

    public Pane get () {
        return this.layoutProvider;
    }

}
