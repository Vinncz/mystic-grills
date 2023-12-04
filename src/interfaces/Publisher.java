package interfaces;

public interface Publisher {

    public void subscribe(String _key, Observer _subscriber);
    public void notifyChanges(String _key, Object _value);

}
