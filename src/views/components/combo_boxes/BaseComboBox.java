package views.components.combo_boxes;

import java.util.ArrayList;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import views.components.interfaces.UsesStrategy;
import views.components.textfields.TextfieldConfig;
import views.guidelines.PageDeclarationGuideline_v1;

public class BaseComboBox implements Observer, UsesStrategy<BaseComboBox>, PageDeclarationGuideline_v1 {

    private static class ListCellWithCustomFont extends javafx.scene.control.ListCell<ComboBoxData> {
        private final Text text;
        private final Font customFont;
        private String color = null;

        ListCellWithCustomFont(Font _font, String _color) {
            this.text = new Text();
            this.customFont = _font;
            this.color = _color;
        }

        @Override
        protected void updateItem(ComboBoxData item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                text.setText(item.getDisplayText());
                text.setFont(customFont);
                text.getStyleClass().add("-fx-text-fill: " + color);
                setGraphic(text);
            } else {
                setGraphic(null);
            }
        }
    }

    private ComboBox<ComboBoxData> objectInCreation;

    public void setData (ArrayList<ComboBoxData> _data) {
        this.data = _data;
    }

    public void setInitialData (ComboBoxData _initData) {
        this.initialData = _initData;
    }

    private Strategy strat = null;
    private ArrayList<ComboBoxData> data;
    private ComboBoxData initialData;

    public BaseComboBox () {
        super();
        objectInCreation = new ComboBox<>();
    }

    public void setSelectedData (ComboBoxData _toBeSelectedData) {
        if ( data.contains(_toBeSelectedData) ) {
            objectInCreation.setValue(_toBeSelectedData);
        }
    }

    public Object getSelectedData () {
        ComboBoxData selectedItem = objectInCreation.getSelectionModel().getSelectedItem();
        return selectedItem.getSupportingData();
    }

    public ComboBox<ComboBoxData> get () {
        initializeScene();
        return this.objectInCreation;
    }

    /**
     * Returns the display object without having to re-render it.
     */
    public ComboBox<ComboBoxData> getInstance () {
        return this.objectInCreation;
    }

    @Override
    public void initializeControls() {
        if ( initialData != null && data.contains(initialData) ) {
            objectInCreation.setValue(initialData);

        } else {
            ComboBoxData cbd = new ComboBoxData("Select value", null);
            objectInCreation.getItems().add(cbd);
            objectInCreation.setValue(cbd);
        }

        for ( ComboBoxData cbd : data ) {
            objectInCreation.getItems().add(cbd);
        }
    }

    @Override
    public void configureElements() {
        objectInCreation.getStyleClass().addAll("comboBox", "cursorPointer");
        objectInCreation.setButtonCell( new ListCellWithCustomFont(Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), TextfieldConfig.FONT_SIZE_SMALLEST), "-primary-white"));
        objectInCreation.setCellFactory( listView -> new ListCellWithCustomFont(Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Regular.otf"), TextfieldConfig.FONT_SIZE_SMALLEST), "-primary-black"));
    }

    @Override
    public void initializeEventListeners() {
        //
    }

    @Override
    public void assembleLayout() {
        //
    }

    @Override
    public void setupScene() {
        //
    }

    @Override
    public BaseComboBox setStrategy(Strategy _strategy) {
        this.strat = _strategy;
        return this;
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if ( this.strat != null ) this.strat.execute((String) _key, _value, this);
    }

}
