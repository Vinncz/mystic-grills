package views.components.card_views;

import java.util.ArrayList;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.layout.Pane;
import views.components.interfaces.UsesStrategy;
import views.components.vboxes.BaseVBox;
import views.guidelines.PageDeclarationGuideline_v1;

public class BaseCardView implements PageDeclarationGuideline_v1, Observer, UsesStrategy<BaseCardView> {

    private Strategy strat = null;
    private BaseVBox objectInCreation;
    private ArrayList<Pane> content;

    public BaseCardView () {
        super();
        objectInCreation = new BaseVBox();
        content = new ArrayList<>();
    }

    public BaseVBox get () {
        initializeScene();
        return this.objectInCreation;
    }

    /**
     * Returns the display object without having to re-render it.
     */
    public BaseVBox getInstance () {
        return this.objectInCreation;
    }

    public BaseCardView setContent (Pane... _contents) {
        ArrayList<Pane> localContent = new ArrayList<>();
        for (Pane p : _contents) {
            localContent.add(p);
        }

        this.content = localContent;
        return this;
    }

    @Override
    public void initializeControls() {
        //
    }

    @Override
    public void configureElements() {
        objectInCreation.withNormalSpacing();
        objectInCreation.getStyleClass().setAll("cardView");
    }

    @Override
    public void initializeEventListeners() {
        //
    }

    @Override
    public void assembleLayout () {
        for (Pane p : content) {
            objectInCreation.getChildren().add(p);
        }
    }

    @Override
    public void setupScene() {
        //
    }

    @Override
    public BaseCardView setStrategy (Strategy _strategy) {
        this.strat = _strategy;
        return this;
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
