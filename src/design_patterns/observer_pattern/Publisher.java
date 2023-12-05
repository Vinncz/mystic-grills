package design_patterns.observer_pattern;

import java.util.ArrayList;

public interface Publisher {

    public void subscribe(String _key, Observer... _subscribers);
    public void subscribeToMany(ArrayList<String> _keys, Observer... _subscribers);
    public void notifyChanges(String _key, Object _value);

}
