package views.components.labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class H2Label extends Label {

    public H2Label (String _message) {
        super(_message);
        this.setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/clash_display/ClashDisplay-Bold.otf"), 56)
        );
        this.getStyleClass().add("h2");
    }

}
