package views;

import application_starter.App;
import commands.AuthenticateCommand;
import interfaces.Observer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import views.components.buttons.CTAButton;
import views.components.buttons.OutlineButton;
import views.components.hboxes.CenteredVerticallyHBox;
import views.components.hboxes.RootElement;
import views.components.textfields.DefaultTextfield;
import views.components.textfields.PasswordTextfield;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.vboxes.Container;
import views.components.vboxes.DefaultVBox;
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

    private VBox warningContainer;
    private Label warningContainerTitleLabel, warningMessageLabel;

    private HBox buttonContainer;
    private Button loginButton, registerButton;

    public LoginPage () {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement    = new RootElement();
        container      = new Container();

        pageIdentifierContainer = new VBox();
            brand          = new H1Label("Mystic Grills").extraBold();
            pageTitle      = new H3Label("Login Portal").extraBold();

        pageContent = new DefaultVBox();
            emailFieldContainer = new DefaultVBox();
                emailLabel    = new H5Label("Email");
                emailField    = new DefaultTextfield("Email here").setId_("email");
            passwordFieldContainer = new DefaultVBox();
                passwordLabel = new H5Label("Password");
                passwordField = new PasswordTextfield("Password here").setId_("password");

        warningContainer = new DefaultVBox();
            warningContainerTitleLabel = new H4Label("Warning:");
            warningMessageLabel        = new H5Label("You entered an incorrect password!");

        buttonContainer = new CenteredVerticallyHBox();
            loginButton     = new CTAButton("Log me in");
            registerButton  = new OutlineButton("I don't have an account");
    }

    @Override
    public void configureElements() {
        App.preferences.subscribe("failedAuth", (Observer) emailField);
        App.preferences.subscribe("failedAuth", (Observer) passwordField);
        App.preferences.subscribe("failedAuth", (Observer) warningContainer);


        warningContainer.setManaged(false);
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setSpacing(24);
        buttonContainer.getStyleClass().addAll("pt-16");

    }

    @Override
    public void initializeEventListeners() {
        emailField.setOnMouseClicked(e -> {
            App.preferences.putValue("failedAuth", false);
        });

        passwordField.setOnMouseClicked(e -> {
            App.preferences.putValue("failedAuth", false);
        });

        registerButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new temp()) );
        });

        loginButton.setOnMouseClicked(e -> {
            if ( new AuthenticateCommand(this).execute() != null ) {
                System.out.println("Successfully logged in!");

            } else {
                App.preferences.putValue("failedAuth", true);
                System.out.println("Authentication failed!");

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
            emailField
        );

        passwordFieldContainer.getChildren().addAll(
            passwordLabel,
            passwordField
        );

        pageContent.getChildren().addAll(
            emailFieldContainer,
            passwordFieldContainer
        );

        warningContainer.getChildren().addAll(
            warningContainerTitleLabel,
            warningMessageLabel
        );

        container.getChildren().addAll(
            pageIdentifierContainer,
            pageContent,
            warningContainer,
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
