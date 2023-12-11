package views.components.combo_boxes;

import java.util.ArrayList;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.ComboBox;
import views.components.interfaces.UsesStrategy;

public class BaseComboBoxBuilder implements Observer, UsesStrategy<BaseComboBoxBuilder> {
    private final BaseComboBox objectInCreation;

    public BaseComboBoxBuilder () {
        objectInCreation = new BaseComboBox();
    }

    public Object getSelectedData () {
        return this.objectInCreation.getSelectedData();
    }

    public BaseComboBoxBuilder withDataOf ( ArrayList<ComboBoxData> _data ) {
        objectInCreation.setData(_data);
        return this;
    }

    public BaseComboBoxBuilder withInitialValueOf ( ComboBoxData _initData ) {
        objectInCreation.setInitialData(_initData);
        return this;
    }

    public ComboBox<ComboBoxData> build () {
        return this.objectInCreation.get();
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        this.objectInCreation.getNotified(_key, _value);
    }

    @Override
    public BaseComboBoxBuilder setStrategy(Strategy _strategy) {
        this.objectInCreation.setStrategy(_strategy);
        return this;
    }

}
