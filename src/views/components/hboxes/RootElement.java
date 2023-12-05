package views.components.hboxes;

import application_starter.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import values.spacings.Spacings;

public class RootElement extends BaseHBox {

    public RootElement () {
        super();
        growsHorizontally();
        growsVertically();
        this.setPadding(new Insets(0, App.stagePadding, 0, App.stagePadding));
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(Spacings.NORMAL_SPACING.value);
    }

}
