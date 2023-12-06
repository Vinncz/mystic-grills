package views.components.vboxes;

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

public class BaseVBox extends VBox implements Observer, CustomizableAlignment<BaseVBox>, CustomizableSpacing<BaseVBox>, CustomizableGrowingDirection<BaseVBox>, SettableIdBuilder<BaseVBox>, UsesStrategy<BaseVBox> {

    private Strategy strat = null;
    private Integer spacing;
    private Pos alignment;

    /**
     * A customized VBox element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's VBox.
     */
    public BaseVBox () {
        super();
        this.spacing = Spacings.NORMAL_SPACING.value;
        this.alignment = Pos.TOP_LEFT;
        build();
    }

    @Override
    public BaseVBox setId_ (String _id) {
        setId(_id);
        return this;
    }

    @Override
    public BaseVBox growsHorizontally () {
        HBox.setHgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseVBox growsVertically () {
        setVgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseVBox centerHorizontally () {
        this.alignment = Pos.TOP_CENTER;
        return build();
    }

    @Override
    public BaseVBox centerVertically () {
        this.alignment = Pos.CENTER_LEFT;
        return build();
    }

    @Override
    public BaseVBox centerOnBothAxis () {
        this.alignment = Pos.CENTER;
        return build();
    }

    @Override
    public BaseVBox withNoSpacing () {
        this.spacing = Spacings.NO_SPACING.value;
        return build();
    }

    @Override
    public BaseVBox withTightSpacing () {
        this.spacing = Spacings.TIGHT_SPACING.value;
        return build();
    }

    @Override
    public BaseVBox withNormalSpacing () {
        this.spacing = Spacings.NORMAL_SPACING.value;
        return build();
    }

    @Override
    public BaseVBox withLooseSpacing () {
        this.spacing = Spacings.LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseVBox withVeryLooseSpacing () {
        this.spacing = Spacings.VERY_LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseVBox withCustomSpacing (Integer _spacing) {
        this.spacing = _spacing;
        return build();
    }

    public BaseVBox build () {
        setAlignment(this.alignment);
        setSpacing(this.spacing);

        return this;
    }

    @Override
    public BaseVBox setStrategy (Strategy _strat) {
        this.strat = _strat;
        return this;
    }

    @Override
    public void getNotified (Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
