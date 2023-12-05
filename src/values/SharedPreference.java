package values;

import java.util.HashMap;
import java.util.List;

import design_patterns.observer_pattern.Observer;
import design_patterns.observer_pattern.Publisher;

import java.util.ArrayList;
import java.util.Collections;

public class SharedPreference implements Publisher {
    private HashMap<String, List<Observer>> subscribers;
    private HashMap<String, Object> preferences;

    /**
     * Adds the passed on _subscriber argument into a pool of other subscribers, that is also subscribing for a given key.
     */
    @Override
    public void subscribe(String _key, Observer... _subscribers) {
        for (Observer o : _subscribers) {
            subscribers.computeIfAbsent(_key, k -> new ArrayList<>()).add(o);

        }
    }

    /**
     * Adds the passed on {@code _subscribers} argument into a pool of other subscribers, that is also subscribing to the passed on {@code _keys}.
     */
    public void subscribeToMany(ArrayList<String> _keys, Observer... _subscribers) {
        for (String _key: _keys) {
            for (Observer _o : _subscribers) {
                subscribers.computeIfAbsent(_key, k -> new ArrayList<>()).add(_o);

            }
        }
    }

    /**
     * Notifies every subscriber for a given key, that a new object has replaced its old value.
     */
    @Override
    public void notifyChanges (String _key, Object _value) {
        List<Observer> keySubscribers = subscribers.getOrDefault(_key, Collections.emptyList());
        for (Observer observer : keySubscribers) {
            observer.getNotified(_key, _value);
        }
    }

    public SharedPreference () {
        preferences = new HashMap<>();
        subscribers = new HashMap<>();

    }

    /**
     * Associates the specified value with the specified key in this SharedPreference. If the SharedPreference previously contained a mapping for the key, the old value is replaced.
     *
     * @param _key • The key with which the specified value is to be associated
     * @param _value • value to be associated with the specified key
     */
    public void putValue (String _key, Object _value) {
        Object o = preferences.put(_key, _value);
        if ( o != _value ) {
            System.out.println("New value incoming for key " + _key + ", old value is " + o + " and the value is " + _value);
            notifyChanges(_key, _value);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this SharedPreference contains no mapping for the key.
     *
     * @param _key • The key whose associated value is to be returned.
     * @return The value associated with said key.
     */
    public Object getValue (String _key) {
        return preferences.get(_key);

    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param _key • The key whose mapping is to be removed from the SharedPreference
     * @return {@code true} if the mapping for said key is able to be set to null, {@code false} otherwise.
     */
    public Boolean removeValue (String _key) {
        return preferences.remove(_key) != null ? true : false;

    }

    /**
     * Returns true if this SharedPreference contains a mapping for the specified key.
     *
     * @param _key • The key whose presence in this SharedPreference is to be tested.
     * @return {@code true} if this SharedPreference contains a mapping for the specified key.
     */
    public Boolean containsKey (String _key) {
        return preferences.containsKey(_key);

    }

}
