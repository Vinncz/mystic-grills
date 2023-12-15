package views;

import java.util.ArrayList;
import java.util.HashMap;

import application_starter.App;
import controllers.MenuItemController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.MenuItem;
import views.components.buttons.CTAButton;
import views.components.card_views.BaseCardView;
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

            VBox content1 = new BaseVBox();
                menuItemNameLabel = new H4Label(menuItem.getMenuItemName()).withBoldFont();
                menuItemDescLabel = new Label(menuItem.getMenuItemDescription());
                
            VBox content2 = new BaseVBox();
                priceTitle = new H5Label("Price").withLightFont();
                menuItemPriceLabel = new H4Label("Rp" + Integer.toString(menuItem.getMenuItemPrice()) + ",-").withBlackFont();

            baseNumberfieldBuilder = new BaseNumberfieldBuilder().withMaximumValueOf(10).withMinimumValueOf(0).withInitialValueOf(0);
            baseNumberfieldObj = baseNumberfieldBuilder.build();

            content1.getChildren().addAll(
                menuItemNameLabel,
                menuItemDescLabel
            );

            content2.getChildren().addAll(
                priceTitle,
                menuItemPriceLabel
            );

            BaseCardView cardView = new BaseCardView();

            cardView.setContent(
                content1,
                content2,
                baseNumberfieldObj
            );

            cardViews.add(cardView);
            numberFields.add(baseNumberfieldObj);
        }
    }

    @Override
    public void configureElements() {
        
    }

    @Override
    public void initializeEventListeners() {
    
        myOrdersBtn.setOnMouseClicked(e -> {
            
        });

        checkoutBtn.setOnMouseClicked(e -> {
            
            HashMap<MenuItem,Integer> checkout_menuItems = new HashMap<>();

            for (BaseNumberfield numInput : numberFields){
                
                Integer quantity = Integer.parseInt(numInput.getInputField().getText());

                if(quantity > 0){
                    
                    MenuItem menuItem = menuItems.get(numberFields.indexOf(numInput));
                    checkout_menuItems.put(menuItem, quantity);
                }
            }

            
            App.preferences.putValue(
                App.PASSING_ORDERS_CCHANNEL_FOR_CHECKOUT,
                checkout_menuItems
            );
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
