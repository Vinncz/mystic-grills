package views.components.labels;

public class H3Label extends HLabel {

    public H3Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_SMALL);
        this.getStyleClass().add("h3");
    }

}
