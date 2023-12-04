package views.components.vboxes;

import interfaces.Observer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DefaultVBox extends VBox implements Observer {

    public DefaultVBox () {
        super();
        HBox.setHgrow(this, Priority.ALWAYS);
        setSpacing(VBoxConfig.DEFAULT_SPACING);
    }

    @Override
    public void getNotified(String _key, Object _value) {
        if (_key.equals("failedAuth") && (Boolean) _value == true) {
            this.setVisible(true);
            this.setManaged(true);

        } else {
            this.setVisible(false);
            this.setManaged(false);
        }
    }

}
