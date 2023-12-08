package views.components.combo_boxes;

import java.util.ArrayList;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.ComboBox;
import views.components.interfaces.UsesStrategy;
import views.guidelines.PageDeclarationGuideline_v1;

public class BaseComboBox implements Observer, UsesStrategy<BaseComboBox>, PageDeclarationGuideline_v1 {

    private ComboBox<ComboBoxData> objectInCreation;

    private Strategy strat = null;
    private ArrayList<ComboBoxData> data;
    private ComboBoxData initialData;

    public BaseComboBox (ArrayList<ComboBoxData> _data, ComboBoxData _initData) {
        super();
        objectInCreation = new ComboBox<>();
        this.data = _data;
        this.initialData = _initData;
    }

    public void setInitialData (ComboBoxData _initData) {
        this.initialData = _initData;
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

    @Override
    public void initializeControls() {
        for ( ComboBoxData cbd : data ) {
            objectInCreation.getItems().add(cbd);
        }
    }

    @Override
    public void configureElements() {
        objectInCreation.setValue(initialData);
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
