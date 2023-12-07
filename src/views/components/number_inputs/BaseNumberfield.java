package views.components.number_inputs;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import views.components.buttons.BaseButton;
import views.components.hboxes.BaseHBox;
import views.components.interfaces.FontVariants;
import views.components.textfields.DefaultTextfield;
import views.guidelines.PageDeclarationGuideline_v1;

public class BaseNumberfield extends BaseHBox implements PageDeclarationGuideline_v1 {

    private Integer minimum, maximum, initValue;
    private Button decrement, increment;
    private TextField inputField;

    public BaseNumberfield () {
        super.withTightSpacing();
        super.centerContentBothAxis();
    }

    @Override
    public void initializeControls() {
        inputField = new DefaultTextfield("");
        decrement  = new BaseButton("-").withBoldFont().withSizeOf(FontVariants.FONT_SIZE_SMALLER);
        increment  = new BaseButton("+").withBoldFont().withSizeOf(FontVariants.FONT_SIZE_SMALLER);
    }

    @Override
    public void configureElements() {
        inputField.setText(this.initValue.toString());
        decrement.setStyle("-fx-background-radius: 8px;");
        increment.setStyle("-fx-background-radius: 8px;");
    }

    @Override
    public void initializeEventListeners() {
        makeInputOnlyAcceptNumbers();

        decrement.setOnMouseClicked(e -> {
            String  inputVal      = inputField.getText();
            Integer cleanInputVal = Integer.parseInt(inputVal);

            if ( cleanInputVal > minimum ) {
                inputField.setText( (--cleanInputVal).toString() );
            }
        });

        increment.setOnMouseClicked(e -> {
            String  inputVal      = inputField.getText();
            Integer cleanInputVal = Integer.parseInt(inputVal);

            if ( cleanInputVal < maximum ) {
                inputField.setText( (++cleanInputVal).toString() );
            }
        });
    }

    private void makeInputOnlyAcceptNumbers() {
        inputField.textProperty().addListener((_observable, _oldValue, _newValue) -> {

            Boolean newValueIsEmptyOrNotANumber = _newValue.isBlank() || !_newValue.matches("-?\\d*");
            if ( newValueIsEmptyOrNotANumber ) {
                _newValue = _oldValue;

            } else {
                try {
                    int safeNewValue = Integer.parseInt(_newValue);

                    Boolean newValueIsOutOfBounds = safeNewValue < minimum || safeNewValue > maximum;
                    if ( newValueIsOutOfBounds ) {
                        _newValue = _oldValue;
                    }

                } catch (NumberFormatException _parseFailedDueToContaminationOfNonNumbers) {
                    _newValue = _oldValue;

                }
            }

            inputField.setText(_newValue);
        });
    }

    @Override
    public void assembleLayout() {
        getChildren().addAll(
            decrement,
            inputField,
            increment
        );
    }

    @Override
    public void setupScene() {
        //
    }

    /*
     * GETTERs & SETTERS
     */
    public BaseNumberfield withMinimumValueOf (Integer _minValue) {
        this.minimum = _minValue;
        return this;
    }

    public BaseNumberfield withMaximumValueOf (Integer _maxValue) {
        this.maximum = _maxValue;
        return this;
    }

    public BaseNumberfield withInitialValueOf (Integer _initialValue) {
        this.initValue = _initialValue;
        return this;
    }

    public Button getDecrementButton () {
        return this.decrement;
    }

    public Button getIncrement () {
        return this.increment;
    }

    public TextField getInputField () {
        return this.inputField;
    }

}
