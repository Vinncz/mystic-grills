package views.components.labels;

public class H1Label extends HLabel {

    public H1Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_LARGE);
        this.getStyleClass().add("h1");
    }

}
