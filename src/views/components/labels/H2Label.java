package views.components.labels;

public class H2Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H2Label serve to be the the second-biggest and act as the headline for an article.
     *
     * @param _message
     */
    public H2Label (String _message) {
        super(_message);
        super.setSize(LabelConfig.FONT_SIZE_MEDIUM);
        this.getStyleClass().add("h2");
    }

}
