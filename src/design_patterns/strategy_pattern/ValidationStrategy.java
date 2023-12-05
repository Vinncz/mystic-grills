package design_patterns.strategy_pattern;

import java.util.ArrayList;

import values.strings.ValidationState;

public interface ValidationStrategy<T> extends Strategy {

    public T setRegisteredErrorWatchList (ArrayList<ValidationState> _registeredError);
    @Override
    public void execute (String _key, Object _value, Object... _params);
    public void switchToErrorState (String _key, Object _value);
    public void revertToNormalState(String _key, Object _value);

}
