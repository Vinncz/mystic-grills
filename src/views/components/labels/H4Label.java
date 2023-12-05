package views.components.labels;

public class H4Label extends HLabel {

    public H4Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_SMALLER);
        super.bold();
        this.getStyleClass().add("h4");
    }

}
