package views.components.labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class HeaderLabel extends Label {

    public HeaderLabel (String _message) {
        super(_message);
        this.setFont(
            new Font("Thunder Black LC", 12)
        );
    }

}
