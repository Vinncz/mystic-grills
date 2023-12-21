package views;

import java.util.ArrayList;

import controllers.MenuItemController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import views.components.card_views.BaseCardView;
import views.components.number_inputs.BaseNumberfield;
import views.components.number_inputs.BaseNumberfieldBuilder;

public class MenuItemManagement2 {

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private BorderPane pageIdentifierContainer;
    private Label pageTitle;
    private Button AddNew;

    private FlowPane pageContent;
    
    private ArrayList<BaseCardView> cardViews;
    private ArrayList<MenuItem> menuItems; 
    
     private Label priceTitle;
    private Label menuItemNameLabel , menuItemDescLabel , menuItemPriceLabel;
    private BaseNumberfieldBuilder baseNumberfieldBuilder;
    private BaseNumberfield baseNumberfieldObj;
    private ArrayList<BaseNumberfield> numberFields;

    private MenuItemController menuItemController;
}
