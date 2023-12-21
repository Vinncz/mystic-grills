package views;

import java.util.ArrayList;

import application_starter.App;
import controllers.MenuItemController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import models.MenuItem;
import views.components.buttons.CTAButton;
import views.components.card_views.BaseCardView;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.number_inputs.BaseNumberfield;
import views.components.number_inputs.BaseNumberfieldBuilder;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class CustomerDashboard extends BorderPane implements PageDeclarationGuideline_v1  {

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private BorderPane pageIdentifierContainer;
    private Label pageTitle;

    private FlowPane pageContent;

    private BorderPane buttonContainer;
    private Button myOrdersBtn , checkoutBtn;

    private ArrayList<BaseCardView> cardViews;
    private ArrayList<MenuItem> menuItems;

    private Label priceTitle;
    private Label menuItemNameLabel , menuItemDescLabel , menuItemPriceLabel;
    private BaseNumberfieldBuilder baseNumberfieldBuilder;
    private BaseNumberfield baseNumberfieldObj;
    private ArrayList<BaseNumberfield> numberFields;

    private MenuItemController menuItemController;

    public CustomerDashboard(){
        initializeScene();        
    }

    @Override
    public void initializeControls() {
    
        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BorderPane();
            pageTitle = new H1Label("Available Menu").withBoldFont().withAlternateFont();
            myOrdersBtn = new CTAButton("My Orders").withSizeOf(16);
        
        pageContent = new FlowPane();
            numberFields = new ArrayList<>();
            cardViews  = new ArrayList<>();
        
        buttonContainer = new BorderPane();
            checkoutBtn = new CTAButton("check out").withSizeOf(16);
        
        menuItemController = new MenuItemController();
        menuItems = menuItemController.getAll();

        for(MenuItem menuItem : menuItems){
        
            BaseCardView cardView = new BaseCardView();

                VBox content1 = new BaseVBox();
                    menuItemNameLabel = new H4Label(menuItem.getMenuItemName()).withBoldFont();
                    menuItemDescLabel = new Label(menuItem.getMenuItemDescription());

                HBox linePane = new BaseHBox();        
                    Line line = new Line(0, 0, 350, 0); 
                
                VBox content2 = new BaseVBox().withTightSpacing();
                    priceTitle = new H5Label("Price").withLightFont();
                    menuItemPriceLabel = new H4Label("Rp" + Integer.toString(menuItem.getMenuItemPrice()) + ",-").withBlackFont();
                    baseNumberfieldBuilder = new BaseNumberfieldBuilder().withMaximumValueOf(10).withMinimumValueOf(0).withInitialValueOf(0);
                    baseNumberfieldObj = baseNumberfieldBuilder.build();

            line.prefWidth(370);
            line.setStyle("-fx-stroke: #C5C5C5;"); 


            content1.getChildren().addAll(
                menuItemNameLabel,
                menuItemDescLabel,
                line
            );

            linePane.getChildren().add(line);

            content2.getChildren().addAll(
                priceTitle,
                menuItemPriceLabel
            );

            cardView.setContent(
                content1,
                linePane,
                content2,
                baseNumberfieldObj
            );

            cardViews.add(cardView);
            numberFields.add(baseNumberfieldObj);
        }
    }

    @Override
    public void configureElements() {
        
        for(BaseCardView cardView : cardViews){
            cardView.getInstance().setPrefWidth(400);
        }

        pageContent.setVgap(15);
        pageContent.setHgap(15);

    }

    @Override
    public void initializeEventListeners() {
    
        myOrdersBtn.setOnMouseClicked(e -> {
            App.redirectTo(App.sceneBuilder(new MyOrderPageCustomer()));
        });

        checkoutBtn.setOnMouseClicked(e -> {

            ArrayList<MenuItem> menuItems_checkout = new ArrayList<>();
            ArrayList<Integer> quantity_checkout = new ArrayList<>();

            for (BaseNumberfield numInput : numberFields){
                
                Integer quantity = Integer.parseInt(numInput.getInputField().getText());

                if(quantity > 0){
                    
                    MenuItem menuItem = menuItems.get(numberFields.indexOf(numInput));
                    menuItems_checkout.add(menuItem);
                    quantity_checkout.add(quantity);
                }
            }

            App.preferences.putValue(
                App.PASSING_ITEM_ORDER_CHANNEL_FOR_CHECKOUT,
                menuItems_checkout
            );

            App.preferences.putValue(
                App.PASSING_QUANTITY_ORDER_CHANNEL_FOR_CHECKOUT,
                quantity_checkout
            );

            App.redirectTo(App.sceneBuilder(new CheckoutPageCustomer()));
        });

    }

    @Override
    public void assembleLayout() {

        pageIdentifierContainer.setLeft(pageTitle);
        pageIdentifierContainer.setRight(myOrdersBtn);

        buttonContainer.setRight(checkoutBtn);

        for(BaseCardView cardView : cardViews){

            pageContent.getChildren().add(
                cardView.get()
            );
        }

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
        setCenter(scrollSupport);
    }

}
