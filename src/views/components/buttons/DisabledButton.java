package views.components.buttons;

public class DisabledButton extends BaseButton {

    public DisabledButton (String _textForButton) {
        super(_textForButton);
        super.disabledVariant();
        setDisable(true);
    }

}
