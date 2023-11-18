package test.src.values.strings;

public enum ValidationMessages {
    GENERIC_INVALID_ARGUMENT("Invalid input!");

    public String value;
    private ValidationMessages (String _value) {
        this.value = _value;
    }
}
