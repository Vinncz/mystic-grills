package views.components.vboxes;

import application_starter.App;
import javafx.geometry.Insets;
import values.SYSTEM_PROPERTIES;

public class Container extends BaseVBox {

    public Container () {
        super();
        growsHorizontally();
        setMinWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        setMaxWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_WIDTH.value));
        setPadding(new Insets(App.stagePadding * 2, 0, 0, 0));
    }

}
