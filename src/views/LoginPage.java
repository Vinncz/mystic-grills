package views;

import application_starter.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import values.SYSTEM_PROPERTIES;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.guidelines.PageDeclarationGuideline_v1;

public class LoginPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private HBox rootElement;
    private VBox container;

    private Label brand, pageTitle;
    private Button loginButton, registerButton;

    public LoginPage () {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement    = new HBox();
        container      = new VBox();
        brand          = new H1Label("Mystic Grills");
        pageTitle      = new H3Label("Login Portal");
        loginButton    = new Button("Log me in");
        registerButton = new Button("I don't have an account");
    }

    @Override
    public void configureElements() {
        container.setMinWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        // container.setStyle("-fx-background-color: red;");
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setPadding(new Insets(App.stagePadding, 0, 0, 0));
        rootElement.setPadding(new Insets(0, App.stagePadding, 0, App.stagePadding));
        rootElement.setAlignment(Pos.TOP_CENTER);

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
        container.getChildren().addAll(
            brand,
            pageTitle,
            loginButton,
            registerButton
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
