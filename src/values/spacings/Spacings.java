package values.spacings;

public enum Spacings {
    NO_SPACING(0),
    TIGHT_SPACING(8),
    NORMAL_SPACING(16),
    LOOSE_SPACING(24),
    VERY_LOOSE_SPACING(32);

    public Integer value;
    private Spacings (Integer _value) {
        this.value = _value;
    }
}
