package views.components.radio_buttons;

import java.util.ArrayList;

import design_patterns.strategy_pattern.Strategy;
import javafx.scene.layout.Pane;
import views.components.interfaces.UsesStrategy;

public class BaseRadioButtonGroupBuilder implements UsesStrategy<BaseRadioButtonGroupBuilder> {

    private BaseRadioButtonGroup objectInCreation;
    private Pane layoutProvider;
    private ArrayList<RadioButtonData> buttonData;
    private RadioButtonData initialValue;
    private Strategy strat;

    public BaseRadioButtonGroupBuilder () {
        super();

        initialValue = new RadioButtonData();
        buttonData = new ArrayList<>();
        layoutProvider = new Pane();
    }

    public BaseRadioButtonGroupBuilder withLayoutProviderOf (Pane _layoutProvider) {
        this.layoutProvider = _layoutProvider;
        return this;
    }

    public BaseRadioButtonGroupBuilder withButtonDataOf (ArrayList<RadioButtonData> _buttonData) {
        this.buttonData = _buttonData;
        return this;
    }

    public BaseRadioButtonGroupBuilder withInitialValueOf (RadioButtonData _buttonData) {
        this.initialValue = _buttonData;
        return this;
    }

    public BaseRadioButtonGroup get () {
        objectInCreation = new BaseRadioButtonGroup (buttonData, initialValue, layoutProvider);
        return objectInCreation;
    }

    public Pane build () {
        objectInCreation = new BaseRadioButtonGroup (buttonData, initialValue, layoutProvider);
        objectInCreation.setStrategy(strat);
        return objectInCreation.get();
    }

    @Override
    public BaseRadioButtonGroupBuilder setStrategy(Strategy _strategy) {
        this.strat = _strategy;
        return this;
    }

}
