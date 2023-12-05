package views.components.labels;

public class H5Label extends HLabel {

    public H5Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_SMALLEST);
        super.bold();
        this.getStyleClass().add("h5");
    }

}
