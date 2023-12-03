package views.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class OutlineButton extends Button {

    public OutlineButton (String _message) {
        super(_message);
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), ButtonConfig.FONT_SIZE - 4)
        );
        this.getStyleClass().add("outlineButton");
        this.getStyleClass().add("underline");
        this.getStyleClass().add("cursorPointer");
        this.getStyleClass().add("letterSpacingLoose");

    }

}
