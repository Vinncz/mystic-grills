package design_patterns.strategy_pattern;

import java.util.ArrayList;

import values.strings.ValidationState;

public interface ValidationStrategy<T> extends Strategy {

    /**
     * Sets a list of ValidationState-errors-to-monitor within the implementing class.
     *
     * @param _registeredError An ArrayList of ValidationState errors to observe.
     * This list guides the class to observe for specific errors' flag changes in the SharedPreferences.
     *
     * @param _registeredError (OLD EXPLANATION) An ArrayList of ValidationState errors to monitor. <br></br>
     * So that whenever a component gets notified that <b> a value has changed in the SharedPreference </b>,
     * it checks for whether itself gotta do something, by comparing whether the key for that SharedPreference's value is part of its own List of ValidationState errors.
     */
    public T setRegisteredErrorWatchList (ArrayList<ValidationState> _registeredError);

    /**
     * Defines what the component should do, when it gets notified that: <b> a key's value, of which it is subscribed to in the SharedPreference, changed. </b>
     * <br></br>
     * Here, the implementors should compare the {@code _key} argument with their error list,
     * determining actions based on the identified error.
     *
     * @param _key • The SharedPreference  _key, whose value changed
     * @param _value • The new value for the given SharedPreference key
     * @param _params • Supporting arguments to help the implementors to take actions correctly
     */
    @Override
    public void execute (String _key, Object _value, Object... _params);

    /**
     * Defines what the component should do, when it identifies an error (that is part of its error-watchlist) occur.
     *
     * @param _key
     * @param _value
     */
    public void switchToErrorState (String _key, Object _value);

    /**
     * Defines what the component should do, when it identifies an error (that is part of its error-watchlist) has been dealt with.
     *
     * @param _key
     * @param _value
     */
    public void revertToNormalState(String _key, Object _value);

}
