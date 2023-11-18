package values.strings;

public enum DatabaseMessages {
    CONNECTION_FAILED       ("Failed to connect with the database"),
    DRIVER_NOT_FOUND        ("JDBC driver not found"),
    AUTOCOMMIT_FALSE_FAILURE("Cannot set database's autocommit to false");

    public String value;
    private DatabaseMessages (String _value) {
        this.value = _value;
    }

}
