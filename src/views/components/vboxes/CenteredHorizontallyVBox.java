package views.components.vboxes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CenteredHorizontallyVBox extends VBox {

    public CenteredHorizontallyVBox () {
        super();
        HBox.setHgrow(this, Priority.ALWAYS);
        setAlignment(Pos.TOP_CENTER);
        setSpacing(VBoxConfig.DEFAULT_SPACING);
    }

}
