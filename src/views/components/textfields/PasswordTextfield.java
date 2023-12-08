package views.components.textfields;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;
import views.components.interfaces.SettableIdBuilder;
import views.components.interfaces.UsesStrategy;

public class PasswordTextfield extends PasswordField implements Observer, SettableIdBuilder<PasswordTextfield>, UsesStrategy<PasswordTextfield> {

    private Strategy strat = null;

    /**
     * A customized PasswordField element that encorporate Builder, Strategy, and Observer pattern, which was built on top JavaFX's PasswordField.
     */
    public PasswordTextfield (String _placeholder) {
        super();
        setPromptText(_placeholder);
        setAlignment(Pos.TOP_LEFT);
        initialize();
    }

    private void initialize () {
        setFont(
            Font.loadFont(getClass().getResourceAsStream("/views/fonts/cabinet_grotesk/CabinetGrotesk-Bold.otf"), TextfieldConfig.FONT_SIZE_SMALLER)
        );
        getStyleClass().add("passwordTextfield");
    }

    @Override
    public PasswordTextfield setId_ (String _id) {
        setId(_id);
        return this;
    }

    @Override
    public PasswordTextfield setStrategy (Strategy _strat) {
        this.strat = _strat;
        return this;
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String) _key, _value, this);
    }

}
