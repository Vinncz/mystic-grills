package views;

import java.util.ArrayList;

import application_starter.App;
import controllers.MenuItemController;
import models.MenuItem;
import values.SYSTEM_PROPERTIES;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.buttons.EditButton;
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

public class MenuItemManagementPage extends BorderPane implements PageDeclarationGuideline_v1{

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private BorderPane pageIdentifierContainer;
    private Label pageTitle;
    private Button addNewButton;

    private FlowPane pageContent;
    
    private ArrayList<BaseCardView> cardViews;
    private ArrayList<MenuItem> menuItems; 
    
    private Label priceTitle;
    private Label menuItemNameLabel , menuItemDescLabel , menuItemPriceLabel;
    private Button editButton, deleteButton;

    private MenuItemController menuItemController;

    public MenuItemManagementPage()
    {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement = new RootElement();
        container = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BorderPane();
            pageTitle = new H1Label("Menu Item Management").withBoldFont().withAlternateFont();
            addNewButton = new CTAButton("Add New").withSizeOf(16);

        pageContent = new FlowPane();
            cardViews = new ArrayList<>();     

        configureMenuItem();
    }

    public void configureMenuItem()
    {
        menuItemController = new MenuItemController();
        menuItems = menuItemController.getAll();   
        for(MenuItem menuItem : menuItems){

            VBox content1 = new BaseVBox().withTightSpacing();
                menuItemNameLabel = new H4Label(menuItem.getMenuItemName()).withBoldFont();
                menuItemDescLabel = new Label(menuItem.getMenuItemDescription());

            VBox content2 = new BaseVBox().withNoSpacing();
                priceTitle = new H5Label("Price").withLightFont();
                menuItemPriceLabel = new H4Label("Rp" + Integer.toString(menuItem.getMenuItemPrice()) + ",-").withBlackFont();

            VBox content3 = new BaseVBox().withTightSpacing();
                editButton = new EditButton("Edit").withSizeOf(16);
                deleteButton = new DestructiveButton("Delete").withSizeOf(16);

            content1.getChildren().addAll(
                menuItemNameLabel,
                menuItemDescLabel
            );

            content2.getChildren().addAll(
                  priceTitle,
                  menuItemPriceLabel
            );
            
            content3.getChildren().addAll(
                editButton,
                deleteButton
            );
             
            BaseCardView cardView = new BaseCardView();

            Pane baseVBox = cardView.get();

            Insets margin = new Insets(10,0,0,30); 

            FlowPane.setMargin(baseVBox, margin);

            cardView.setContent(
                content1,
                content2,
                content3
            );

            editButton.setOnMouseClicked(e -> {
                App.preferences.putValue(App.PASSING_ID_CHANNEL_FOR_MODIFICATION, menuItem.getMenuItemId());
                App.redirectTo(App.sceneBuilder(new EditMenuItemPage()));
            });

            deleteButton.setOnMouseClicked(e->{
                menuItemController.delete(menuItem.getMenuItemId());
                cardViews.remove(cardView);
                pageContent.getChildren().remove(cardView.getInstance());
                for (BaseCardView remainingCardView : cardViews) {
                    pageContent.getChildren().add(remainingCardView.get());
                }
            });

            cardViews.add(cardView);
        }
    }

    @Override
    public void configureElements() {
        pageContent.setPrefWidth(800);
        pageContent.setPrefHeight(500);
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setStyle("-fx-background-color: #FFFFFF;");
        Border pageContentBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        pageContent.setBorder(pageContentBorder);
    }

    @Override
    public void initializeEventListeners() {
        addNewButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new NewMenuItemPage()) );
        });

    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.setLeft(pageTitle);
        pageIdentifierContainer.setRight(addNewButton);

        for(BaseCardView cardView : cardViews){

            pageContent.getChildren().add(
                cardView.get()
            );
            
        }

        container.getChildren().addAll(
            pageIdentifierContainer,
            pageContent
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
