package views.components.labels;

import views.components.interfaces.FontVariants;

public class H5Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H5Label serve to be the the smallest and act as an element which regular text uses.
     *
     * @param _message
     */
    public H5Label (String _message) {
        super(_message);
        super.setSize(FontVariants.FONT_SIZE_SMALLEST);
        super.withBoldFont();
        this.getStyleClass().add("h5");
    }

}
