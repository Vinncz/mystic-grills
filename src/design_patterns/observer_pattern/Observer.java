package design_patterns.observer_pattern;

public interface Observer {

    /**
     * Defines what the implementors should do when it gets notified that: <b> a Publisher </b>, of which it is subscribed to, <b> decided to broadcast something that it believes its subscribers should know </b>.
     *
     * @param _key
     * @param _value
     */
    public void getNotified (Object _key, Object _value);

}
