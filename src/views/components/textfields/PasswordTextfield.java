package views.components.textfields;

import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;

public class PasswordTextfield extends PasswordField {

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

}
