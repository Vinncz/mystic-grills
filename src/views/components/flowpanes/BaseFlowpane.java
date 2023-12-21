package views.components.flowpanes;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import values.spacings.Spacings;
import views.components.interfaces.CustomizableAlignment;
import views.components.interfaces.CustomizableGrowingDirection;
import views.components.interfaces.CustomizableSpacing;
import views.components.interfaces.SettableIdBuilder;
import views.components.interfaces.UsesStrategy;

public class BaseFlowpane extends FlowPane implements Observer, CustomizableAlignment<BaseFlowpane>, CustomizableSpacing<BaseFlowpane>, CustomizableGrowingDirection<BaseFlowpane>, SettableIdBuilder<BaseFlowpane>, UsesStrategy<BaseFlowpane> {

    private Integer spacing;
    private Pos alignment;
    private Strategy strat = null;

    public BaseFlowpane () {
        super();
        setHgap(Spacings.NORMAL_SPACING.value);
        setVgap(Spacings.NORMAL_SPACING.value);
    }

    public BaseFlowpane build () {
        setHgap(spacing);
        setVgap(spacing);
        setAlignment(alignment);

        return this;
    }

    @Override
    public BaseFlowpane setStrategy(Strategy _strategy) {
        this.strat = _strategy;
        return this;
    }

    @Override
    public BaseFlowpane setId_(String _id) {
        setId(_id);
        return this;
    }

    @Override
    public BaseFlowpane growsHorizontally() {
        HBox.setHgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseFlowpane growsVertically() {
        VBox.setVgrow(this, Priority.ALWAYS);
        return this;
    }

    @Override
    public BaseFlowpane withNoSpacing() {
        this.spacing = Spacings.NO_SPACING.value;
        return build();
    }

    @Override
    public BaseFlowpane withTightSpacing() {
        this.spacing = Spacings.TIGHT_SPACING.value;
        return build();
    }

    @Override
    public BaseFlowpane withNormalSpacing() {
        this.spacing = Spacings.NORMAL_SPACING.value;
        return build();
    }

    @Override
    public BaseFlowpane withLooseSpacing() {
        this.spacing = Spacings.LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseFlowpane withVeryLooseSpacing() {
        this.spacing = Spacings.VERY_LOOSE_SPACING.value;
        return build();
    }

    @Override
    public BaseFlowpane withCustomSpacing(Integer _spacing) {
        this.spacing = _spacing;
        return build();
    }

    @Override
    public BaseFlowpane centerContentHorizontally() {
        this.alignment = Pos.TOP_CENTER;
        return build();
    }

    @Override
    public BaseFlowpane centerContentVertically() {
        this.alignment = Pos.CENTER_LEFT;
        return build();
    }

    @Override
    public BaseFlowpane centerContentBothAxis() {
        this.alignment = Pos.CENTER;
        return build();
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
