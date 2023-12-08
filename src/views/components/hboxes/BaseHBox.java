package views.components.hboxes;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import values.spacings.Spacings;
import views.components.interfaces.CustomizableAlignment;
import views.components.interfaces.CustomizableGrowingDirection;
import views.components.interfaces.CustomizableSpacing;
import views.components.interfaces.SettableIdBuilder;
import views.components.interfaces.UsesStrategy;

public class BaseHBox extends HBox implements Observer, CustomizableAlignment<BaseHBox>, CustomizableSpacing<BaseHBox>, CustomizableGrowingDirection<BaseHBox>, SettableIdBuilder<BaseHBox>, UsesStrategy<BaseHBox> {

    private Integer spacing;
    private Pos alignment;
    private Strategy strat = null;

    /**
     * A customized HBox element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's HBox.
     */
    public BaseHBox () {
        super();
        setSpacing(Spacings.NORMAL_SPACING.value);
        setAlignment(Pos.TOP_LEFT);
    }

    @Override
    public BaseHBox setId_ (String _id) {
        setId(_id);
        return this;
    }

    @Override
    public BaseHBox growsHorizontally () {
        setHgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseHBox growsVertically () {
        VBox.setVgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseHBox centerContentHorizontally () {
        this.alignment = Pos.TOP_CENTER;
        return build();
    }

    @Override
    public BaseHBox centerContentVertically () {
        this.alignment = Pos.CENTER_LEFT;
        return build();
    }

    @Override
    public BaseHBox centerContentBothAxis () {
        this.alignment = Pos.CENTER;
        return build();
    }

    @Override
    public BaseHBox withNoSpacing () {
        this.spacing = Spacings.NO_SPACING.value;
        return build();
    }

    @Override
    public BaseHBox withTightSpacing () {
        this.spacing = Spacings.TIGHT_SPACING.value;
        return build();
    }

    @Override
    public BaseHBox withNormalSpacing () {
        this.spacing = Spacings.NORMAL_SPACING.value;
        return build();
    }

    @Override
    public BaseHBox withLooseSpacing () {
        this.spacing = Spacings.LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseHBox withVeryLooseSpacing () {
        this.spacing = Spacings.VERY_LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseHBox withCustomSpacing (Integer _spacing) {
        this.spacing = _spacing;
        return build();
    }

    public BaseHBox build () {
        setAlignment(this.alignment);
        setSpacing(this.spacing);

        return this;
    }

    @Override
    public BaseHBox setStrategy (Strategy _strat) {
        this.strat = _strat;
        return this;
    }

    @Override
    public void getNotified (Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
