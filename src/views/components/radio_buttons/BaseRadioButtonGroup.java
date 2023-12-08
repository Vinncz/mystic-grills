package views.components.radio_buttons;

import java.util.ArrayList;

import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.Strategy;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import views.components.hboxes.BaseHBox;
import views.components.labels.H5Label;
import views.guidelines.PageDeclarationGuideline_v1;

public class BaseRadioButtonGroup implements Observer, PageDeclarationGuideline_v1 {

    private Strategy strat;

    private Integer listOfButtonDataSize;

    private ArrayList<RadioButtonData> buttonData;
    private RadioButtonData initialData;
    private ToggleGroup buttonToggleGroup;

    private Pane layoutProvider;
    private ArrayList<RadioButton> buttons;
    private ArrayList<BaseRadioButtonElement> displayElements;

    public class BaseRadioButtonElement {
        public Pane wrapper;
        public Label label;
    }

    public void setSelectedData (Object _toBeSelectedData) {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            if ( buttons.get(i).getUserData().equals(_toBeSelectedData) ) {
                buttons.get(i).fire();
            }
        }
    }

    public Object getSelectedData () {
        return this.buttonToggleGroup.getSelectedToggle().getUserData();
    }

    public Pane get () {
        initializeScene();
        return this.layoutProvider;
    }

    public BaseRadioButtonGroup (ArrayList<RadioButtonData> _buttonData, RadioButtonData _initialData, Pane _layoutProvider) {
        super();

        this.listOfButtonDataSize = _buttonData.size();
        this.buttonData = _buttonData;
        this.initialData = _initialData;
        this.layoutProvider = _layoutProvider;
        this.buttons = new ArrayList<>();
        this.displayElements = new ArrayList<>();
        this.buttonToggleGroup = new ToggleGroup();
    }

    @Override
    public void initializeControls() {
        declareHowManyRadioButtonAsTheDataProvided();

        declareHowManyButtonElementAsTheDataProvided();
    }

    private void declareHowManyButtonElementAsTheDataProvided() {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            BaseRadioButtonElement displayElement = new BaseRadioButtonElement(){
                {
                    wrapper = new BaseHBox().withTightSpacing();
                    label = new H5Label("");
                }
            };

            displayElements.add(displayElement);
        }
    }

    private void declareHowManyRadioButtonAsTheDataProvided() {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            RadioButton rb = new RadioButton();
            buttons.add(rb);
        }
    }

    @Override
    public void configureElements() {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            RadioButton rb = buttons.get(i);
            configureRadioButtonProperty(i, rb);

            BaseRadioButtonElement el = displayElements.get(i);
            configureButtonElementProperty(i, rb, el);

            Boolean initialDataWantsThisToBeActivated = buttonData.get(i).equals(initialData);
            if ( initialDataWantsThisToBeActivated ) {
                activateSaidButton(rb, el);

            }
        }
    }

    private void activateSaidButton(RadioButton rb, BaseRadioButtonElement el) {
        rb.fire();
        el.wrapper.getStyleClass().setAll("radioButtonSelected", "cursorPointer");
    }

    private void configureButtonElementProperty(int i, RadioButton rb, BaseRadioButtonElement el) {
        el.label.setText( buttonData.get(i).getDisplayText() );
        el.wrapper.getChildren().addAll(rb);
        el.wrapper.getStyleClass().setAll("radioButton", "cursorPointer");
    }

    private void configureRadioButtonProperty(int i, RadioButton rb) {
        rb.setVisible(false);
        rb.setManaged(false);
        rb.setToggleGroup(buttonToggleGroup);
        rb.setUserData(buttonData.get(i).getSupportingData());
    }

    @Override
    public void initializeEventListeners() {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            RadioButton rb = buttons.get(i);
            BaseRadioButtonElement el = displayElements.get(i);

            el.wrapper.setOnMouseClicked(e -> {
                rb.fire();
            });
        }

        buttonToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                int i = buttons.indexOf(((RadioButton) newToggle));
                BaseRadioButtonElement el = displayElements.get(i);
                el.wrapper.getStyleClass().setAll("radioButtonSelected", "cursorPointer");

                if (oldToggle != null) {
                    i = buttons.indexOf(((RadioButton) oldToggle));
                    el = displayElements.get(i);
                    el.wrapper.getStyleClass().setAll("radioButton", "cursorPointer");
                }
            }

        });

    }

    @Override
    public void assembleLayout() {
        for ( int i = 0; i < listOfButtonDataSize; i++ ) {
            Pane p = displayElements.get(i).wrapper;
            Label l = displayElements.get(i).label;
            p.getChildren().add(l);

            layoutProvider.getChildren().addAll(
                p
            );
        }
    }

    @Override
    public void setupScene() {
        //
    }

    @Override
    public void getNotified(Object _key, Object _value) {
        if (this.strat != null) this.strat.execute((String)_key, _value, this);
    }

    public void setStrategy (Strategy _strategy) {
        this.strat = _strategy;
    }

}
