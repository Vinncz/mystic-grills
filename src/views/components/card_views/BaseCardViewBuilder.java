package views.components.card_views;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import views.components.combo_boxes.BaseComboBoxBuilder;
import views.components.interfaces.UsesStrategy;

public class BaseCardViewBuilder implements Observer, UsesStrategy<BaseCardViewBuilder> {

    private final BaseCardView objectInCreation;

    public BaseCardViewBuilder () {
        objectInCreation = new BaseCardView();
    }

    @Override
    public BaseCardViewBuilder setStrategy(Strategy _strategy) {
        this.objectInCreation.setStrategy(_strategy);
        return this;
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        this.objectInCreation.getNotified((String) _key, _value);
    }

}
