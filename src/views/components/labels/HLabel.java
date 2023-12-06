package views.components.labels;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import views.components.interfaces.UsesStrategy;

public class HLabel extends Label implements Observer, UsesStrategy<HLabel> {

    public static final String BLACK = "Black";
    public static final String EXTRA_BOLD = "Extrabold";
    public static final String BOLD = "Bold";
    public static final String SEMI_BOLD = "Semibold";
    public static final String MEDIUM = "Medium";
    public static final String REGULAR = "Regular";
    public static final String LIGHT = "Light";

    private Boolean usesAlternateFont = false;
    private Strategy strat = null;
    private String variant = REGULAR;
    private Integer fontSize = LabelConfig.FONT_SIZE_SMALLEST;

    /**
     * A customized Label element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's Label.
     */
    public HLabel (String _message) {
        super(_message);
        withDefaultFont();
    }

    public HLabel setSize (Integer _fontSize) {
        this.fontSize = _fontSize;
        return build();
    }

    public HLabel setMessage (String _message) {
        super.setText(_message);
        return build();
    }

    public HLabel withDefaultFont () {
        this.usesAlternateFont = false;
        return build();
    }

    public HLabel withAlternateFont () {
        this.usesAlternateFont = true;
        return build();
    }

    public HLabel build () {
        if ( this.usesAlternateFont == false ) {
            setFont(
                Font.loadFont(getClass().getResourceAsStream(String.format("/views/fonts/cabinet_grotesk/CabinetGrotesk-%s.otf", this.variant)), this.fontSize)
            );

        } else {
            setFont(
                Font.loadFont(getClass().getResourceAsStream(String.format("/views/fonts/clash_display/ClashDisplay-%s.otf", this.variant)), this.fontSize)
            );
        }

        return this;
    }

    public HLabel black () {
        this.variant = BLACK;
        return build();
    }

    public HLabel extraBold () {
        if (this.usesAlternateFont) {
            this.variant = BOLD;

        } else {
            this.variant = EXTRA_BOLD;

        }
        return build();
    }

    public HLabel semiBold () {
        if (this.usesAlternateFont) {
            this.variant = SEMI_BOLD;

        } else {
            this.variant = MEDIUM;

        }

        return build();
    }

    public HLabel bold () {
        this.variant = BOLD;
        return build();
    }

    public HLabel regular () {
        this.variant = REGULAR.toString();
        return build();
    }

    public HLabel light () {
        this.variant = LIGHT.toString();
        return build();
    }

    @Override
    public HLabel setStrategy (Strategy _strat) {
        this.strat = _strat;
        return build();
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
