package design_patterns.strategy_pattern;

public interface Strategy {

    /**
     * Defines what the component should do, when it gets notified that: <b> a key's value, of which it is subscribed to in the SharedPreference, changed. </b>
     * <br></br>
     * Here, the implementors should compare the {@code _key} argument with their watchlist,
     * for whether they should do something after getting notified that a new value has replaced the old ones.
     *
     * @param _key • The SharedPreference key, whose value changed
     * @param _value • The new value for the given SharedPreference key
     * @param _params • Supporting arguments to help the implementors to take actions correctly
     */
    public void execute (String _key, Object _value, Object... _params);

}
