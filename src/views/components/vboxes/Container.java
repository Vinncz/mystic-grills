package views.components.vboxes;

import application_starter.App;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import values.SYSTEM_PROPERTIES;

public class Container extends VBox {

    /**
     * Should be designated as the only child of RootElement component
     */
    public Container () {
        this.setMinWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        this.setPadding(new Insets(App.stagePadding, 0, 0, 0));
        HBox.setHgrow(this, Priority.ALWAYS);
        setSpacing(VBoxConfig.DEFAULT_SPACING);
    }

}
