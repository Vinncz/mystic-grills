package values.strings;

public enum DatabaseMessages {
    CONNECTION_FAILED
        ("Failed to connect with the database"),

    CONNECTION_CLOSED
        ("Connection closed succesfully"),

    DRIVER_NOT_FOUND
        ("JDBC driver not found"),

    AUTOCOMMIT_TO_FALSE_FAILURE
        ("Unable to set database's autocommit to false"),

    EMPTY_PREPARED_STATEMENT
        ("Prepared Statement is null or empty"),

    PREPARED_STATEMENT_ILLEGAL_ARGUMENT
        ("Illegal argument(s) were received. Check your enumerals assignment or check your query to only modify a single line inside the database"),

    ROLLBACK_FAILURE
        ("Unable to rollback the database"),

    ROLLBACK_SUCCESS
        ("Rollback success"),

    GENERIC_UNASSOCIATED_EXCEPTION
        ("An exception was raised, yet it didn't have any identifier nor message associated with its creation");

    public String value;
    private DatabaseMessages (String _value) {
        this.value = _value;
    }

}
