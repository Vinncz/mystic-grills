package views.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CTAButton extends Button {

    public CTAButton (String _message) {
        super(_message);
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), ButtonConfig.FONT_SIZE)
        );
        this.getStyleClass().add("ctaButton");
        this.getStyleClass().add("cursorPointer");
        this.getStyleClass().add("letterSpacingLoose");

    }

}
