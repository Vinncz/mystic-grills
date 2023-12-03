package views.components.textfields;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class DefaultTextfield extends TextField {

    public DefaultTextfield (String _placeholder) {
        super();
        setPromptText(_placeholder);
        initialize();

    }

    private void initialize () {
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), TextfieldConfig.FONT_SIZE_SMALLER)
        );
        getStyleClass().add("defaultTextfield");
    }

}
