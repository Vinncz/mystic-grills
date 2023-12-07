package views.components.labels;

import views.components.interfaces.FontVariants;

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
        super.setSize(FontVariants.FONT_SIZE_SMALLER);
        super.withBoldFont();
        this.getStyleClass().add("h4");
    }

}
