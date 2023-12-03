package views.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class DestructiveButton extends Button {

    public DestructiveButton (String _message) {
        super(_message);
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), ButtonConfig.FONT_SIZE)
        );
        this.getStyleClass().add("destructiveButton");
        this.getStyleClass().add("cursorPointer");
        this.getStyleClass().add("letterSpacingLoose");

    }

}
