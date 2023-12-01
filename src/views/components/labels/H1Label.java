package views.components.labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class H1Label extends Label {

    public H1Label (String _message) {
        super(_message);
        this.setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/clash_display/ClashDisplay-Bold.otf"), 72)
        );
        this.getStyleClass().add("h1");
    }

}
