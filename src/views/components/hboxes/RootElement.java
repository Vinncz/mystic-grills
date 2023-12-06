package views.components.hboxes;

import application_starter.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import values.spacings.Spacings;

public class RootElement extends BaseHBox {

    /**
     * A variant of the BaseHBox, which is a customized HBox element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's HBox.
     * <br></br>
     * Compared to BaseHBox, RootElement is specialized to serve to be the ONLY ONE parent element for a given Scene.
     *
     * @param _message
     */
    public RootElement () {
        super();
        growsHorizontally();
        growsVertically();
        this.setPadding(new Insets(0, App.stagePadding, 0, App.stagePadding));
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(Spacings.NORMAL_SPACING.value);
    }

}
