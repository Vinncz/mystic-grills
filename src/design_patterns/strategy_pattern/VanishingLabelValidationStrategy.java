package design_patterns.strategy_pattern;

import java.util.ArrayList;

import javafx.scene.control.Label;
import values.strings.ValidationState;

public class VanishingLabelValidationStrategy implements ValidationStrategy<VanishingLabelValidationStrategy> {

    private ArrayList<ValidationState> registeredError = new ArrayList<>();
    private Label objectReference;

    /**
     * A variation of LabelValidationStrategy, that should be passed on as argument into a class that extends from {@code javafx.scene.control.Label}.
     *
     * This class determine which action should be taken by a Label element, when its getNotified() function is called.
     */
    public VanishingLabelValidationStrategy () {}

    @Override
    public VanishingLabelValidationStrategy setRegisteredErrorWatchList (ArrayList<ValidationState> _registeredError) {
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
            if (o instanceof Label) {
                this.objectReference = (Label) o;
            }
        }
    }

    @Override
    public void switchToErrorState(String _key, Object _value) {
        this.objectReference.setText(_key);
        this.objectReference.getStyleClass().add(ValidationState.ERROR_STATE_TEXT.value);
        this.objectReference.setVisible(true);
        this.objectReference.setManaged(true);
    }

    @Override
    public void revertToNormalState(String _key, Object _value) {
        this.objectReference.setText("");
        this.objectReference.getStyleClass().remove(ValidationState.ERROR_STATE_TEXT.value);
        this.objectReference.setVisible(false);
        this.objectReference.setManaged(false);
    }

}
