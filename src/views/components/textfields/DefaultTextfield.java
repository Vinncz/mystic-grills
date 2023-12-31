package views.components.textfields;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import views.components.interfaces.UsesStrategy;

public class DefaultTextfield extends TextField implements Observer, UsesStrategy<DefaultTextfield> {

    private Strategy strat = null;

    /**
     * A customized TextField element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's TextField.
     */
    public DefaultTextfield (String _placeholder) {
        super();
        setPromptText(_placeholder);
        setAlignment(Pos.TOP_LEFT);
        initialize();
    }

    private void initialize () {
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), TextfieldConfig.FONT_SIZE_SMALLER)
        );
        getStyleClass().add("defaultTextfield");
    }

    public DefaultTextfield setId_ (String _id) {
        setId(_id);
        return this;
    }

    @Override
    public DefaultTextfield setStrategy (Strategy _strat) {
        this.strat = _strat;
        return this;
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
