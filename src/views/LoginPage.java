package views;

import java.util.ArrayList;
import java.util.stream.Collectors;

import application_starter.App;
import controllers.UserController;
import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.LabelValidationStrategy;
import design_patterns.strategy_pattern.TextfieldValidationStrategy;
import design_patterns.strategy_pattern.VanishingLabelValidationStrategy;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import repositories.UserRepository;
import values.SYSTEM_PROPERTIES;
import values.strings.ValidationState;
import views.components.buttons.CTAButton;
import views.components.buttons.OutlineButton;
import views.components.hboxes.RootElement;
import views.components.textfields.DefaultTextfield;
import views.components.textfields.PasswordTextfield;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H5Label;
import views.guidelines.PageDeclarationGuideline_v1;

public class LoginPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private Label brand, pageTitle;

    private VBox pageContent;
    private VBox emailFieldContainer, passwordFieldContainer;

    private Label emailLabel, passwordLabel;
    private TextField emailField, passwordField;
    private Label emailWarnLabel, passwordWarnLabel;

    private VBox buttonContainer;
    private Button loginButton, registerButton;

    public LoginPage () {
        initializeScene();
    }

    private ArrayList<ValidationState> errorToWatchForEmailRelatedElement = new ArrayList<>(){
        {
            add(ValidationState.EMPTY_EMAIL);
            add(ValidationState.UNREGISTERED_EMAIL);
        }
    };
    private ArrayList<ValidationState> errorToWatchForPasswordRelatedElement = new ArrayList<>(){
        {
            add(ValidationState.EMPTY_PASSWORD);
            add(ValidationState.INCORRECT_PASSWORD);
            add(ValidationState.INVALID_PASSWORD_LENGTH);
        }
    };

    @Override
    public void initializeControls() {
        rootElement    = new RootElement();
        container      = new Container().centerHorizontally();

        pageIdentifierContainer = new BaseVBox().withNoSpacing().centerHorizontally();
            brand     = new H1Label("Mystic Grills").bold().withAlternateFont();
            pageTitle = new H3Label("Login Portal").extraBold();

        pageContent = new BaseVBox().withNormalSpacing();
            emailFieldContainer = new BaseVBox().withTightSpacing();
                emailLabel      = new H5Label("Email")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForEmailRelatedElement)
                                        );
                emailField      = new DefaultTextfield("Email here")
                                        .setStrategy(
                                            new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForEmailRelatedElement)
                                        );
                emailWarnLabel  = new H5Label("warn")
                                        .setStrategy(
                                            new VanishingLabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForEmailRelatedElement)
                                        );

            passwordFieldContainer = new BaseVBox().withTightSpacing();
                passwordLabel      = new H5Label("Password")
                                        .setStrategy(
                                            new LabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElement)
                                        );
                passwordField      = new PasswordTextfield("Password here")
                                        .setStrategy(
                                            new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElement)
                                        );
                passwordWarnLabel  = new H5Label("warn")
                                        .setStrategy(
                                            new VanishingLabelValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchForPasswordRelatedElement)
                                        );

        buttonContainer = new BaseVBox().withLooseSpacing().centerOnBothAxis();
            loginButton     = new CTAButton("Log me in");
            registerButton  = new OutlineButton("I don't have an account");
    }

    @Override
    public void configureElements() {
        ArrayList<String> _errorToWatchForEmailRelatedElement = new ArrayList<>(errorToWatchForEmailRelatedElement.stream().map(ValidationState::value).collect(Collectors.toList()));
        ArrayList<String> _errorToWatchForPasswordRelatedElement = new ArrayList<>(errorToWatchForPasswordRelatedElement.stream().map(ValidationState::value).collect(Collectors.toList()));

        App.preferences.subscribeToMany(
            _errorToWatchForPasswordRelatedElement,

            (Observer) passwordLabel,
            (Observer) passwordWarnLabel,
            (Observer) passwordField
        );

        App.preferences.subscribeToMany(
            _errorToWatchForEmailRelatedElement,

            (Observer) emailLabel,
            (Observer) emailField,
            (Observer) emailWarnLabel
        );

        emailWarnLabel.setVisible(false);
            emailWarnLabel.setManaged(false);
        passwordWarnLabel.setVisible(false);
            passwordWarnLabel.setManaged(false);

        // pageContent.getStyleClass().add("redBg");
        pageContent.setMaxWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setSpacing(24);
        buttonContainer.getStyleClass().addAll("pt-16");

    }

    @Override
    public void initializeEventListeners() {
        emailField.setOnMouseClicked(e -> {
            System.out.println(errorToWatchForEmailRelatedElement.size());
            for (ValidationState vs : errorToWatchForEmailRelatedElement) {
                App.preferences.putValue(vs.value, false);
            }
        });

        passwordField.setOnMouseClicked(e -> {
            System.out.println(errorToWatchForPasswordRelatedElement.size());
            for (ValidationState vs : errorToWatchForPasswordRelatedElement) {
                App.preferences.putValue(vs.value, false);
            }
        });

        registerButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new temp()) );
        });

        loginButton.setOnMouseClicked(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            UserController uc = new UserController();
            UserRepository.AuthenticationReturnDatatype authResult = uc.authenticateUser(email, password);

            if ( authResult.getState() != null ) {
                App.preferences.putValue(authResult.getState().value, true);

            } else {
                // App.redirectTo( App.sceneBuilder(new LoginPage()) );
            }
        });

    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            brand,
            pageTitle
        );

        buttonContainer.getChildren().addAll(
            loginButton,
            registerButton
        );

        emailFieldContainer.getChildren().addAll(
            emailLabel,
            emailField,
            emailWarnLabel
        );

        passwordFieldContainer.getChildren().addAll(
            passwordLabel,
            passwordField,
            passwordWarnLabel
        );

        pageContent.getChildren().addAll(
            emailFieldContainer,
            passwordFieldContainer
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
    public void setupScene() {
        setCenter(rootElement);

    }

}
