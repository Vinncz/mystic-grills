package design_patterns.observer_pattern;

import java.util.ArrayList;

public interface Publisher {

    /**
     * Adds the passed on {@code _subscribers} argument into a pool of other subscribers, that is also subscribing for a given {@code _topic}.
     *
     * @param _topic • Topic to be subscribed to
     * @param _subscribers • Who will be subscribing
     */
    public void subscribe(Object _topic, Observer... _subscribers);

    /**
     * Adds the passed on {@code _subscribers} argument into a pool of other subscribers, that is also subscribing for a given {@code _topics}.
     *
     * @param _topics • Topics to be subscribed to
     * @param _subscribers • Who will be subscribing
     */
    public void subscribeToMany(ArrayList<Object> _keys, Observer... _subscribers);

    /**
     * Defines what the implementors should do to notify its subscibers.
     *
     * @param _topic • To briefly tell what is this broadcast is all about.
     * @param _supportingArgument • Attachment(s) that reinforces the topic.
     */
    public void notifyChanges(Object _topic, Object _supportingArgument);

}
