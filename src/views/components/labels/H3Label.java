package views.components.labels;

import views.components.interfaces.FontVariants;

public class H3Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H3Label serve to be the the third-biggest and act as a subtitle for an article's title or an indicator for which page you're at.
     *
     * @param _message
     */
    public H3Label (String _message) {
        super(_message);
        super.setSize(FontVariants.FONT_SIZE_SMALL);
        this.getStyleClass().add("h3");
    }

}
