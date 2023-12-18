package views.components.labels;

import views.components.interfaces.FontVariants;

public class H6Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H6Label serve to be even smaller than the smallest and sometimes act as an element which regular text uses.
     *
     * @param _message
     */
    public H6Label (String _message) {
        super(_message);
        super.setSize(FontVariants.FONT_SIZE_SMALLESTEST);
        super.withBoldFont();
        this.getStyleClass().add("h6");
    }

}
