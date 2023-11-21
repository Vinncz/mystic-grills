package values;

public enum DATABASE_MODIFICATION_POLICY {
    MAXIMUM_ROW_FOR_MODIFICATION
        (10),

    ROW_MODIFICATION_WARN_TRESHOLD
        (2);

    public Integer value;
    private DATABASE_MODIFICATION_POLICY (Integer _value) {
        this.value = _value;
    }
}
