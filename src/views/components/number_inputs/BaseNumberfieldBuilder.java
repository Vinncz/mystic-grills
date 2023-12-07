package views.components.number_inputs;

public class BaseNumberfieldBuilder {
    private final BaseNumberfield objectInCreation = new BaseNumberfield();

    public BaseNumberfieldBuilder () {

    }

    public BaseNumberfieldBuilder withMinimumValueOf(Integer minValue) {
        this.objectInCreation.withMinimumValueOf(minValue);
        return this;
    }

    public BaseNumberfieldBuilder withMaximumValueOf(Integer maxValue) {
        this.objectInCreation.withMaximumValueOf(maxValue);
        return this;
    }

    public BaseNumberfieldBuilder withInitialValueOf(Integer initialValue) {
        this.objectInCreation.withInitialValueOf(initialValue);
        return this;
    }

    public BaseNumberfield build () {
        objectInCreation.initializeScene();
        return objectInCreation;
    }
}
