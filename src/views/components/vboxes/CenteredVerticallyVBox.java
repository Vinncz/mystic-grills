package views.components.vboxes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CenteredVerticallyVBox extends VBox {

    public CenteredVerticallyVBox () {
        super();
        HBox.setHgrow(this, Priority.ALWAYS);
        setSpacing(VBoxConfig.DEFAULT_SPACING);
        setAlignment(Pos.CENTER);
    }

}
