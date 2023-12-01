package views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.components.labels.HeaderLabel;
import views.guidelines.PageDeclarationGuideline_v1;

public class LoginPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private Stage primaryStageReference;
    private VBox rootElement;

    private Label title;

    public LoginPage (Stage _primaryStageReference) {
        this.primaryStageReference = _primaryStageReference;
        initializeScene();

    }

    @Override
    public void initializeControls() {
        rootElement = new VBox();
        title = new HeaderLabel("Login Page");
    }

    @Override
    public void configureElements() {
        //

    }

    @Override
    public void initializeEventListeners() {
        //

    }

    @Override
    public void assembleLayout() {
        rootElement.getChildren().addAll(
            title
        );

    }

    @Override
    public void setupScene() {
        setCenter(rootElement);

    }

    @Override
    public void redirectTo(Scene _targetScene) {
        primaryStageReference.setScene(_targetScene);

    }

}
