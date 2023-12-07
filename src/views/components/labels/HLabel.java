package views.components.labels;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import views.components.interfaces.CustomizableFont;
import views.components.interfaces.FontVariants;
import views.components.interfaces.UsesStrategy;

public class HLabel extends Label implements Observer, UsesStrategy<HLabel>, CustomizableFont<HLabel> {

    private Boolean usesAlternateFont = false;
    private Strategy strat = null;
    private String variant = FontVariants.REGULAR;
    private Integer fontSize = FontVariants.FONT_SIZE_SMALLEST;

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

    @Override
    public HLabel withDefaultFont () {
        this.usesAlternateFont = false;
        return build();
    }

    @Override
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

    @Override
    public HLabel withBlackFont () {
        this.variant = FontVariants.BLACK;
        return build();
    }

    @Override
    public HLabel withExtraBoldFont () {
        if (this.usesAlternateFont) {
            this.variant = FontVariants.BOLD;

        } else {
            this.variant = FontVariants.EXTRA_BOLD;

        }
        return build();
    }

    @Override
    public HLabel withSemiboldFont () {
        if (this.usesAlternateFont) {
            this.variant = FontVariants.SEMI_BOLD;

        } else {
            this.variant = FontVariants.MEDIUM;

        }

        return build();
    }

    @Override
    public HLabel withBoldFont () {
        this.variant = FontVariants.BOLD;
        return build();
    }

    @Override
    public HLabel withRegularFont () {
        this.variant = FontVariants.REGULAR;
        return build();
    }

    @Override
    public HLabel withLightFont () {
        this.variant = FontVariants.LIGHT;
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
