package values.strings;

public enum ValidationState {
    EMPTY_USERNAME
        ("Username must be filled"),

    EMPTY_EMAIL
        ("Email must be filled"),

    DUPLICATE_EMAIL
        ("Said email has an account associated with it"),

    EMPTY_PASSWORD
        ("Password must be filled"),

    INVALID_PASSWORD_LENGTH
        ("Password must at least be longer than 6 characters"),

    EMPTY_CONFIRMATION_PASSWORD
        ("Confirmation password must be filled"),

    INCORRECT_CONFIRMATION_PASSWORD
        ("Confirmation password must be the same as your password"),

    UNREGISTERED_EMAIL
        ("Said email has no account associated with it. Try registering first"),

    INCORRECT_PASSWORD
        ("Incorrect password entered"),

    EMPTY_MENUITEM_NAME
        ("Menu item name must be filled"),
    
    DUPLICATE_MENUITEM_NAME
        ("Menu item name must be unique"),

    EMPTY_MENUITEM_DESCRIPTION
    ("Menu item description must be filled"),

    INVALID_MENUITEM_DESCRIPTION_LENGTH
        ("Menu item description must be more than 10 characters"),
    
    EMPTY_MENUITEM_PRICE
        ("Menu item price must be filled"),

    INVALID_MENUITEM_PRICE_FORMAT
        ("Menu item price must be a valid number"),

    INVALID_MENUITEM_PRICE_RANGE
        ("Menu item price must be greater than or equal to 2.5"),

    GENERIC_INVALID_ARGUMENT
        ("Invalid input!"),

    ERROR_STATE_BACKGROUND
        ("errorRedBackground"),

    ERROR_STATE_TEXT
        ("errorRedText");

    public String value;
    private ValidationState (String _value) {
        this.value = _value;
    }

    public static ValidationState find (String _value) {
        for (ValidationState state : ValidationState.values()) {
            if (state.value.equals(_value)) {
                return state;
            }
        }
        return null;
    }

    public String value () {
        return this.value;
    }

}
