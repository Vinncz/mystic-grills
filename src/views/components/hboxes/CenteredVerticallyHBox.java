package views.components.hboxes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CenteredVerticallyHBox extends HBox {

    public CenteredVerticallyHBox () {
        super();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(HBoxConfig.DEFAULT_SPACING);
        setHgrow(this, Priority.ALWAYS);
    }

}
