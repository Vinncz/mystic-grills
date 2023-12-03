package views.components.hboxes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CenteredHorizontallyHBox extends HBox {

    public CenteredHorizontallyHBox () {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(HBoxConfig.DEFAULT_SPACING);
        setHgrow(this, Priority.ALWAYS);
    }

}
