package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import views.components.buttons.CTAButton;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class CheckoutPage extends BorderPane implements PageDeclarationGuideline_v1{
    
    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private HBox pageIdentifierContainer;
    private Label pageTitle;

    private VBox pageContent;
    private HBox topPageContent;
    private VBox content;

    private HBox buttonContainer;
    private Button confirmOrderBtn;

    public CheckoutPage()
    {
        initializeScene();
    }

    @Override
    public void initializeControls() {

        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseHBox();    
            pageTitle = new H1Label("Checkout Menu");
        
        pageContent = new BaseVBox();
            topPageContent = new BaseHBox();
            content = new BaseVBox();
    

        buttonContainer = new BaseHBox();
        confirmOrderBtn = new CTAButton("Confirm Order");
    }   

    @Override
    public void configureElements() {

    }

    @Override
    public void initializeEventListeners() {
        
    }

    @Override
    public void assembleLayout() {

    }

    @Override
    public void setupScene() {

    }
}
