package views.components.labels;

public class H2Label extends HLabel {

    public H2Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_MEDIUM);
        this.getStyleClass().add("h2");
    }

}
