package views.components.interfaces;

import design_patterns.strategy_pattern.Strategy;

public interface UsesStrategy <T> {

    /**
     * Accepts a {@code _strategy} to be executed, when something is triggered.
     *
     * @param _strategy • The strategy which will be executed, when something is triggered
     */
    public T setStrategy (Strategy _strategy);

}
