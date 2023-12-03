package views.components.labels;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class HLabel extends Label {

    public static final String BLACK = "Black";
    public static final String EXTRA_BOLD = "Extrabold";
    public static final String BOLD = "Bold";
    public static final String REGULAR = "Regular";
    public static final String LIGHT = "Light";

    private String variant = REGULAR;
    private Integer fontSize = LabelConfig.FONT_SIZE_SMALLEST;

    public HLabel setSize (Integer _fontSize) {
        this.fontSize = _fontSize;
        build();
        return this;
    }

    public HLabel setMessage (String _message) {
        super.setText(_message);
        build();
        return this;
    }

    public HLabel build () {
        setFont(
            Font.loadFont(getClass().getResourceAsStream(String.format("/views/fonts/cabinet_grotesk/CabinetGrotesk-%s.otf", this.variant)), this.fontSize)
        );
        return this;
    }

    public HLabel black () {
        this.variant = BLACK;
        build();
        return this;
    }

    public HLabel extraBold () {
        this.variant = EXTRA_BOLD;
        build();
        return this;
    }

    public HLabel bold () {
        this.variant = BOLD;
        build();
        return this;
    }

    public HLabel regular () {
        this.variant = REGULAR.toString();
        build();
        return this;
    }

    public HLabel light () {
        this.variant = LIGHT.toString();
        build();
        return this;
    }

    public HLabel (String _message) {
        super(_message);
        build();
    }

}
