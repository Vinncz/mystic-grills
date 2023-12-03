package views;

import application_starter.App;
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
import views.components.labels.H5Label;
import views.components.vboxes.Container;
import views.components.vboxes.DefaultVBox;
import views.guidelines.PageDeclarationGuideline_v1;

public class LoginPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifier;
    private Label brand, pageTitle;

    private VBox pageContent;
    private VBox emailFieldContainer, passwordFieldContainer;

    private Label emailLabel, passwordLabel;
    private TextField emailField, passwordField;

    private HBox buttonContainer;
    private Button loginButton, registerButton;

    public LoginPage () {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement    = new RootElement();
        container      = new Container();

        pageIdentifier = new VBox();
            brand          = new H1Label("Mystic Grills").extraBold();
            pageTitle      = new H3Label("Login Portal").extraBold();

        pageContent = new DefaultVBox();
            emailFieldContainer = new DefaultVBox();
                emailLabel = new H5Label("Email");
                emailField = new DefaultTextfield("Email here");
            passwordFieldContainer = new DefaultVBox();
                passwordLabel = new H5Label("Password");
                passwordField = new PasswordTextfield("Password here");

        buttonContainer = new CenteredVerticallyHBox();
        loginButton    = new CTAButton("Log me in");
        registerButton = new OutlineButton("I don't have an account");
    }

    @Override
    public void configureElements() {
        pageIdentifier.setStyle("-fx-padding: 0 0 24 0;");
        pageContent.setSpacing(24);
        pageContent.setStyle("-fx-padding: 0 0 120 0");

    }

    @Override
    public void initializeEventListeners() {
        registerButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new temp()) );
        });

        loginButton.setOnMouseClicked(e -> {
            App.preferences.putValue("why", e);
        });

    }

    @Override
    public void assembleLayout() {
        pageIdentifier.getChildren().addAll(
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

        container.getChildren().addAll(
            pageIdentifier,
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
