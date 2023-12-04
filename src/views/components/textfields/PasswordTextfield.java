package views.components.textfields;

import interfaces.Observer;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;

public class PasswordTextfield extends PasswordField implements Observer {

    public PasswordTextfield (String _placeholder) {
        super();
        setPromptText(_placeholder);
        initialize();
    }

    private void initialize () {
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), TextfieldConfig.FONT_SIZE_SMALLER)
        );
        getStyleClass().add("passwordTextfield");
    }

    public PasswordTextfield setId_ (String _id) {
        setId(_id);
        return this;
    }

    @Override
    public void getNotified(String _key, Object _value) {
        if (_key.equals("failedAuth") && (Boolean) _value == true) {
            this.setStyle("-fx-background-color: red");

        } else {
            this.setStyle("");
        }
    }

}
