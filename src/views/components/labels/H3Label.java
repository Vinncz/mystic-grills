package views.components.labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class H3Label extends Label {

    public H3Label (String _message) {
        super(_message);
        this.setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), 32)
        );
        this.getStyleClass().add("h3");
    }

}
