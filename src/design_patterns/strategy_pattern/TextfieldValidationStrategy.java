package design_patterns.strategy_pattern;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import values.strings.ValidationState;

public class TextfieldValidationStrategy implements ValidationStrategy<TextfieldValidationStrategy> {

    private ArrayList<ValidationState> registeredError = new ArrayList<>();
    private TextField objectReference = null;

    /**
     * A subclass of ValidationStrategy, that should be passed on as argument into a class that extends from {@code javafx.scene.control.TextField}.
     *
     * This class determine which action should be taken by a TextField element, when its getNotified() function is called.
     */
    public TextfieldValidationStrategy () {}

    public TextfieldValidationStrategy setRegisteredErrorWatchList (ArrayList<ValidationState> _registeredError) {
        this.registeredError = _registeredError;
        return this;
    }

    @Override
    public void execute(String _key, Object _value, Object... _params) {
        findTheControlTarget(_params);

        if ( registeredError.contains(ValidationState.find(_key)) && (Boolean) _value == true ) {
            switchToErrorState(_key, _value);

        } else if ( registeredError.contains(ValidationState.find(_key)) && (Boolean) _value == false ) {
            revertToNormalState(_key, _value);

        }
    }

    private void findTheControlTarget(Object... _params) {
        for (Object o : _params) {
            if (o instanceof TextField) {
                this.objectReference = (TextField) o;
            }
        }
    }

    @Override
    public void switchToErrorState (String _key, Object _value) {
        this.objectReference.getStyleClass().add("redBg");
    }

    @Override
    public void revertToNormalState (String _key, Object _value) {
        this.objectReference.getStyleClass().remove("redBg");
    }

}
