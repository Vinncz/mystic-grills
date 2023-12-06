package views.components.labels;

public class H4Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H4Label serve to be the the second-smallest and act as an emphasizer for a span of text.
     *
     * @param _message
     */
    public H4Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_SMALLER);
        super.bold();
        this.getStyleClass().add("h4");
    }

}
