package views.components.interfaces;

import design_patterns.strategy_pattern.Strategy;

public interface UsesStrategy <T> {

    public T setStrategy (Strategy _strat);

}
