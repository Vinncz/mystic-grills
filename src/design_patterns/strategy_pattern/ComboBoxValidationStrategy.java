package design_patterns.strategy_pattern;

import java.util.ArrayList;

import values.strings.ValidationState;
import views.components.combo_boxes.BaseComboBox;

public class ComboBoxValidationStrategy implements ValidationStrategy<ComboBoxValidationStrategy> {

    private ArrayList<ValidationState> registeredError = new ArrayList<>();
    private BaseComboBox objectReference;

    public ComboBoxValidationStrategy () {}

    @Override
    public ComboBoxValidationStrategy setRegisteredErrorWatchList(ArrayList<ValidationState> _registeredError) {
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

    @Override
    public void switchToErrorState(String _key, Object _value) {
        this.objectReference.getInstance().getStyleClass().addAll(ValidationState.ERROR_STATE_BACKGROUND.value);
    }

    @Override
    public void revertToNormalState(String _key, Object _value) {
        this.objectReference.getInstance().getStyleClass().removeAll(ValidationState.ERROR_STATE_BACKGROUND.value);
    }

    private void findTheControlTarget(Object... _params) {
        for (Object o : _params) {
            if (o instanceof BaseComboBox) {
                this.objectReference = (BaseComboBox) o;
            }
        }
    }

}
