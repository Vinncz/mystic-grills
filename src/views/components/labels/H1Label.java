package views.components.labels;

import views.components.interfaces.FontVariants;

public class H1Label extends HLabel {

    /**
     * A variant of the base HLabel, which is a customized Label element that encorporate Builder, Strategy, and Observer pattern.
     * <br></br>
     * Compared to other variants, H1Label serve to be the biggest and act as the most eye-catching Label -- often used as a Title.
     *
     * @param _message
     */
    public H1Label (String _message) {
        super(_message);
        super.setSize(FontVariants.FONT_SIZE_LARGE);
        this.getStyleClass().add("h1");
    }

}
