package views.components.scroll_panes;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import views.components.interfaces.CustomizableGrowingDirection;

public class BaseScrollPane extends ScrollPane implements CustomizableGrowingDirection<BaseScrollPane> {

    public BaseScrollPane ( Pane p ) {
        setContent(p);
        setFitToHeight(true);
        setFitToWidth(true);
        growsHorizontally();
        growsVertically();
    }

    @Override
    public BaseScrollPane growsHorizontally() {
        HBox.setHgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseScrollPane growsVertically() {
        VBox.setVgrow(this, Priority.ALWAYS);
        return this;
    }

}
