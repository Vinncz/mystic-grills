package views;

import java.util.ArrayList;
import java.util.stream.Collectors;

import application_starter.App;
import controllers.MenuItemController;
import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.LabelValidationStrategy;
import design_patterns.strategy_pattern.TextfieldValidationStrategy;
import design_patterns.strategy_pattern.VanishingLabelValidationStrategy;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.MenuItem;
import repositories.MenuItemRepository;
import values.strings.ValidationState;
import views.components.buttons.CTAButton;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H5Label;
import views.components.textfields.DefaultTextfield;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;


public class EditMenuItemPage extends BorderPane implements PageDeclarationGuideline_v1
{
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private VBox pageContent;
    private VBox menuNameFieldContainer, menuPriceFieldContainer, menuDescriptionFieldContainer;

    private Label menuNameLabel, menuPriceLabel, menuDescriptionLabel;
    private TextField menuNameField, menuPriceField, menuDescriptionField;
    private Label menuNameWarnLabel , menuPriceWarnLabel , menuDescriptionWarnLabel;

    private VBox buttonContainer;
    private Button saveButton;

    public EditMenuItemPage()
    {
        initializeScene();
    }

    private ArrayList<ValidationState> errorToWatchForMenuNameRelatedElement = new ArrayList<>(){
        {
            add(ValidationState.EMPTY_MENUITEM_NAME);
            add(ValidationState.DUPLICATE_MENUITEM_NAME); 
        }
    };

    private ArrayList<ValidationState> errorToWatchForMenuPriceRelatedElement = new ArrayList<>(){
        {
            add(ValidationState.INVALID_MENUITEM_PRICE_RANGE);
        }
    };

    private ArrayList<ValidationState> errorToWatchForMenuDescriptionRelatedElement = new ArrayList<>(){
        {
            add(ValidationState.INVALID_MENUITEM_DESCRIPTION_LENGTH);
        }
    };

    @Override
    public void initializeControls() 
    {
        rootElement = new RootElement();
        container = new Container();

        pageIdentifierContainer = new BaseVBox();
            pageTitle = new H1Label("Edit Menu Item").withExtraBoldFont();
        
        pageContent = new BaseVBox().withNormalSpacing();
            menuNameFieldContainer = new BaseVBox().withTightSpacing();
                menuNameLabel = new H5Label("Menu Name")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuNameRelatedElement)
                                        );

                menuNameField = new DefaultTextfield("Menu name here")
                                        .setStrategy(
                                            new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuNameRelatedElement)
                                        );

                menuNameWarnLabel = new H5Label("warn")
                                        .setStrategy(
                                            new VanishingLabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuNameRelatedElement)
                                        );
            
            menuPriceFieldContainer = new BaseVBox().withTightSpacing();
                menuPriceLabel = new H5Label("Menu Price")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuNameRelatedElement)
                                        );

                menuPriceField = new DefaultTextfield("Menu price here")
                                        .setStrategy(
                                            new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuPriceRelatedElement)
                                        );

                menuPriceWarnLabel = new H5Label("warn")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuNameRelatedElement)
                                        );

            menuDescriptionFieldContainer = new BaseVBox().withTightSpacing();
                menuDescriptionLabel = new H5Label("Menu Description")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuDescriptionRelatedElement)
                                        );

                menuDescriptionField = new DefaultTextfield("Menu Description here")
                                        .setStrategy(
                                            new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuDescriptionRelatedElement)
                                        );

                menuDescriptionWarnLabel = new H5Label("warn")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForMenuDescriptionRelatedElement)
                                        );
            
        buttonContainer = new BaseVBox().withLooseSpacing();
            saveButton = new CTAButton("Save");
    }

    @Override
    public void configureElements() 
    {
        ArrayList<Object> _errorToWatchForMenuNameRelatedElement = new ArrayList<>(errorToWatchForMenuNameRelatedElement.stream().map(ValidationState::value).collect(Collectors.toList()));
        ArrayList<Object> _errorToWatchForMenuPriceRelatedElement = new ArrayList<>(errorToWatchForMenuPriceRelatedElement.stream().map(ValidationState::value).collect(Collectors.toList()));
        ArrayList<Object> _errorToWatchForMenuDescriptionRelatedElement = new ArrayList<>(errorToWatchForMenuDescriptionRelatedElement.stream().map(ValidationState::value).collect(Collectors.toList()));
        
        App.preferences.subscribeToMany(
            _errorToWatchForMenuNameRelatedElement,

            (Observer) menuNameLabel,
            (Observer) menuNameWarnLabel,
            (Observer) menuNameField
        );

        App.preferences.subscribeToMany(
            _errorToWatchForMenuPriceRelatedElement,

            (Observer) menuPriceLabel,
            (Observer) menuPriceWarnLabel,
            (Observer) menuPriceField
        );

        App.preferences.subscribeToMany(
            _errorToWatchForMenuDescriptionRelatedElement,

            (Observer) menuDescriptionLabel,
            (Observer) menuDescriptionWarnLabel,
            (Observer) menuDescriptionWarnLabel
        );

        menuNameWarnLabel.setVisible(false);
            menuNameWarnLabel.setManaged(false);
        menuPriceWarnLabel.setVisible(false);
            menuPriceWarnLabel.setManaged(false);
        menuDescriptionWarnLabel.setVisible(false);
            menuDescriptionWarnLabel.setManaged(false);

        menuDescriptionField.setMinHeight(200);
        menuDescriptionField.setAlignment(Pos.TOP_LEFT);
    }

    @Override
    public void initializeEventListeners() 
    {
        menuNameField.setOnMouseClicked(e -> {
            for (ValidationState vs : errorToWatchForMenuNameRelatedElement) {
                App.preferences.putValue(vs.value, false);
            }
        });

        menuPriceField.setOnMouseClicked(e -> {
            for (ValidationState vs : errorToWatchForMenuPriceRelatedElement) {
                App.preferences.putValue(vs.value, false);
            }
        });

        menuDescriptionField.setOnMouseClicked(e -> {
            for (ValidationState vs : errorToWatchForMenuDescriptionRelatedElement) {
                App.preferences.putValue(vs.value, false);
            }
        });

        saveButton.setOnMouseClicked(e -> {
            String menuItemName = menuNameField.getText();
            String menuItemPrice = menuPriceField.getText();
            String menuItemDesc = menuDescriptionField.getText();

            MenuItem menuItem = new MenuItem();
            menuItem.setMenuItemName(menuItemName);
            menuItem.setMenuItemPrice(Integer.parseInt(menuItemPrice));
            menuItem.setMenuItemDescription(menuItemDesc);

            MenuItemController menuItemController = new MenuItemController();
            MenuItemRepository.ValidateReturnDatatype result = menuItemController.post(menuItem);

            if ( result.getState() != null ) {
                App.preferences.putValue(result.getState().value, true);

            } else {
                App.redirectTo( App.sceneBuilder(new temp()) );

            }
        });


    }

    @Override
    public void assembleLayout() 
    {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle
        );

        buttonContainer.getChildren().addAll(
            saveButton
        );

        menuNameFieldContainer.getChildren().addAll(
            menuNameLabel,
            menuNameField,
            menuNameWarnLabel
        );

        menuPriceFieldContainer.getChildren().addAll(
            menuPriceLabel,
            menuPriceField,
            menuPriceWarnLabel
        );

        menuDescriptionFieldContainer.getChildren().addAll(
            menuDescriptionLabel,
            menuDescriptionField,
            menuDescriptionWarnLabel
        );

        pageContent.getChildren().addAll(
            menuNameFieldContainer,
            menuPriceFieldContainer,
            menuDescriptionFieldContainer
        );

        container.getChildren().addAll(
            pageIdentifierContainer,
            pageContent,
            buttonContainer
        );

        rootElement.getChildren().addAll(
            container
        );
    }

    @Override
    public void setupScene() 
    {
        setCenter(rootElement);
    }
    
}