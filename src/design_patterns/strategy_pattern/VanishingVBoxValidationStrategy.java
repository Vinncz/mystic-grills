package design_patterns.strategy_pattern;

import java.util.ArrayList;

import javafx.scene.layout.VBox;
import values.strings.ValidationState;

public class VanishingVBoxValidationStrategy implements ValidationStrategy<VanishingVBoxValidationStrategy> {

    private ArrayList<ValidationState> registeredError = new ArrayList<>();
    private VBox objectReference;

    @Override
    public VanishingVBoxValidationStrategy setRegisteredErrorWatchList (ArrayList<ValidationState> _registeredError) {
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
            if (o instanceof VBox) {
                this.objectReference = (VBox) o;
            }
        }
    }

    @Override
    public void switchToErrorState(String _key, Object _value) {
        this.objectReference.setVisible(true);
        this.objectReference.setManaged(true);
    }

    @Override
    public void revertToNormalState(String _key, Object _value) {
        this.objectReference.setVisible(false);
        this.objectReference.setManaged(false);
    }

}
