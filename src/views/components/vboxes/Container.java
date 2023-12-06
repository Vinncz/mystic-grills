package views.components.vboxes;

import application_starter.App;
import javafx.geometry.Insets;
import values.SYSTEM_PROPERTIES;

public class Container extends BaseVBox {

    /**
     * A variant of the BaseVBox, which is a customized HBox element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's HBox.
     * <br></br>
     * Compared to BaseVBox, Container is specialized to serve to be the ONLY ONE child element for the sole RootElement in a given Scene.
     * <br></br>
     * Featuring a clamping max-width, minimum width for safe display, and a top padding for modernity for the element of the page.
     *
     * @param _message
     */
    public Container () {
        super();
        growsHorizontally();
        setMinWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        setMaxWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_WIDTH.value));
        setPadding(new Insets(App.stagePadding * 2, 0, 0, 0));
    }

}
