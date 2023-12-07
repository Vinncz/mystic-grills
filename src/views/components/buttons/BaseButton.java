package views.components.buttons;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import views.components.interfaces.CustomizableFont;
import views.components.interfaces.CustomizableGrowingDirection;
import views.components.interfaces.FontVariants;
import views.components.interfaces.UsesStrategy;

public class BaseButton extends Button implements Observer, UsesStrategy<BaseButton>, CustomizableGrowingDirection<BaseButton>, CustomizableFont<BaseButton> {

    private static final String VARIANT_DESTRUCTIVE    = "destructiveButton";
    private static final String VARIANT_CALL_TO_ACTION = "ctaButton";
    private static final String VARIANT_OUTLINE        = "outlineButton";
    private static final String VARIANT_EDIT           = "editButton";
    private static final String VARIANT_DISABLED       = "disabledButton";

    private Boolean usesAlternateFont = false;
    private Integer fontSize = FontVariants.FONT_SIZE_SMALLEST;
    private String fontVariant = FontVariants.BOLD;

    private Strategy strat = null;
    private String buttonVariant;

    public BaseButton ( String _textForButton ) {
        super(_textForButton);
        this.buttonVariant = VARIANT_CALL_TO_ACTION;
        build();

    }

    public BaseButton ctaVariant () {
        this.buttonVariant = VARIANT_CALL_TO_ACTION;
        return build();
    }

    public BaseButton outlineVariant () {
        this.buttonVariant = VARIANT_OUTLINE;
        return build();
    }

    public BaseButton destructiveVariant () {
        this.buttonVariant = VARIANT_DESTRUCTIVE;
        return build();
    }

    public BaseButton editVariant () {
        this.buttonVariant = VARIANT_EDIT;
        return build();
    }

    public BaseButton disabledVariant () {
        this.buttonVariant = VARIANT_DISABLED;
        return build();
    }

    public BaseButton build () {
        getStyleClass().setAll(buttonVariant, "cursorPointer", "letterSpacingLoose");

        if ( this.usesAlternateFont == false ) {
            setFont(
                Font.loadFont(
                    getClass().getResourceAsStream(
                        String.format(
                            "/views/fonts/cabinet_grotesk/CabinetGrotesk-%s.otf",
                            this.fontVariant
                        )
                    ),

                    this.fontSize
                )
            );

        } else {
            setFont(
                Font.loadFont(
                    getClass().getResourceAsStream(
                        String.format(
                            "/views/fonts/clash_display/ClashDisplay-%s.otf",
                            this.fontVariant
                        )
                    ),

                    this.fontSize
                )
            );
        }

        return this;
    }

    @Override
    public BaseButton growsHorizontally() {
        HBox.setHgrow(this, Priority.ALWAYS);
        return build();
    }

    @Override
    public BaseButton growsVertically() {
        VBox.setVgrow(this, Priority.ALWAYS);
        return build();
    }

    @Override
    public BaseButton setStrategy(Strategy _strategy) {
        this.strat = _strategy;
        return build();
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

    @Override
    public BaseButton withDefaultFont() {
        this.usesAlternateFont = false;
        return build();
    }

    @Override
    public BaseButton withAlternateFont() {
        this.usesAlternateFont = true;
        return build();
    }

	@Override
	public BaseButton withBlackFont() {
		this.fontVariant = FontVariants.BLACK;
        return build();
    }

	@Override
	public BaseButton withExtraBoldFont() {
		if (this.usesAlternateFont) {
            this.fontVariant = FontVariants.BOLD;

        } else {
            this.fontVariant = FontVariants.EXTRA_BOLD;

        }

        return build();
    }

	@Override
	public BaseButton withBoldFont() {
		this.fontVariant = FontVariants.BOLD;
        return build();
    }

	@Override
	public BaseButton withSemiboldFont() {
		if (this.usesAlternateFont) {
            this.fontVariant = FontVariants.SEMI_BOLD;

        } else {
            this.fontVariant = FontVariants.MEDIUM;

        }

        return build();
    }

	@Override
	public BaseButton withRegularFont() {
		this.fontVariant = FontVariants.REGULAR;
        return build();
    }

	@Override
	public BaseButton withLightFont() {
		this.fontVariant = FontVariants.LIGHT;
        return build();
    }

    /**
     * Adds an underline to the text of a button
     */
    public BaseButton underlineTheText () {
        this.getStyleClass().addAll("underline");
        return this;
    }

}
