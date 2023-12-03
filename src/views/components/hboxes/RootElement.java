package views.components.hboxes;

import application_starter.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class RootElement extends HBox {

    /**
     * Should be designated as the parent element for every JavaFX page
     */
    public RootElement () {
        this.setPadding(new Insets(0, App.stagePadding, 0, App.stagePadding));
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(HBoxConfig.DEFAULT_SPACING);
    }

}
