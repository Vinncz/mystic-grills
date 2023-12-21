package views;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import views.guidelines.PageDeclarationGuideline_v1;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.buttons.EditButton;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.hboxes.BaseHBox;
import views.components.vboxes.Container;

public class MenuItemManagementPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private HBox pageIdentifierContainer;
    private Label pageTitle;
    private Button insertNewMenuItemButtonTop;

    private HBox pageContent;
    private VBox firstMenuContainer, secondMenuContainer, insertNewMenuContainer;

    private Label firstMenuTitle, secondMenuTitle, insertNewMenuTitle;
    private Text firstMenuDescription, secondMenuDescription, insertNewMenuDescription;
    private Label firstMenuPrice, secondMenuPrice;
    private Button firstMenuEditButton, secondMenuEditButton, firstMenuDeleteButton, secondMenuDeleteButton, insertNewMenuItemButtonButtom;

    public MenuItemManagementPage()
    {
        initializeScene();
    }

    /**
     * Initialize your scene's various elements here.
     */
    @Override
    public void initializeControls()
    {
        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseHBox().withNormalSpacing().centerContentVertically();
            pageTitle = new H1Label("Menu Item Management").withExtraBoldFont();
            insertNewMenuItemButtonTop = new CTAButton("Add New");

        pageContent = new BaseHBox().withNormalSpacing();
                firstMenuContainer = new BaseVBox();
                    firstMenuTitle = new H5Label("Bakso Gepeng Ala Titoti").withBoldFont().withAlternateFont();
                    firstMenuDescription = new Text("Bakso telur gede, ditemenin sama 3 bakso halus");
                    firstMenuPrice = new H5Label("24.000");
                    firstMenuEditButton = new EditButton("Edit");
                    firstMenuDeleteButton = new DestructiveButton("Delete");

                secondMenuContainer = new BaseVBox();
                    secondMenuTitle = new H5Label("Boba Milk Tea Tjap Njonja").withBoldFont().withAlternateFont();
                    secondMenuDescription = new Text("Susu segar yang dipadukan dengan kemanisan gula jawa. Dijamin bakal nagih!");
                    secondMenuPrice = new H5Label("24.000");
                    secondMenuEditButton = new EditButton("Edit");
                    secondMenuDeleteButton = new DestructiveButton("Delete");

                insertNewMenuContainer = new BaseVBox();
                    insertNewMenuTitle = new H5Label("Insert New Menu Item Here").withBoldFont().withAlternateFont();
                    insertNewMenuDescription = new Text("Simply click this button and we will take you there");
                    insertNewMenuItemButtonButtom = new CTAButton("Add New");
    }

    /**
     * Customize your initialized control elements here.
     * <br></br>
     * The recommended actions may include:
     * <ul>
     *     <li>Styling up your declared control elements</li>
     *     <li>Set up custom properties for a particular control elements</li>
     * </ul>
     */
    public void configureElements()
    {
        pageContent.setPrefWidth(800);
        pageContent.setPrefHeight(500);
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setSpacing(24);
        pageContent.setStyle("-fx-background-color: #FFFFFF;");
        Border pageContentBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        pageContent.setBorder(pageContentBorder);

        firstMenuContainer.setMaxHeight(310);
        firstMenuContainer.setMaxWidth(150);
        firstMenuTitle.setWrapText(true);
        firstMenuDescription.setWrappingWidth(200);
        Border firstMenuBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        firstMenuContainer.setBorder(firstMenuBorder);
        firstMenuContainer.setPadding(new Insets(20, 20, 20, 20));
        BaseHBox.setMargin(firstMenuContainer, new Insets(10,10,0,30));
        firstMenuTitle.setStyle("-fx-text-fill: #463634;");
        firstMenuDescription.setStyle("-fx-text-fill: #463634;");
        firstMenuPrice.setStyle("-fx-text-fill: #463634;");
        firstMenuEditButton.setStyle("-fx-background-radius: 5;-fx-padding: 5 62;");
        firstMenuDeleteButton.setStyle("-fx-background-radius: 5;-fx-padding: 5 50;");
        BaseVBox.setMargin(firstMenuPrice, new Insets(17, 0, 3, 0));
        
        
        secondMenuContainer.setMaxHeight(310);
        secondMenuContainer.setMaxWidth(150);
        secondMenuTitle.setWrapText(true);
        secondMenuDescription.setWrappingWidth(200);
        Border secondMenuBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        secondMenuContainer.setBorder(secondMenuBorder);
        secondMenuContainer.setPadding(new Insets(20, 20, 20, 20));
        BaseHBox.setMargin(secondMenuContainer, new Insets(10,10,0,10));
        secondMenuTitle.setStyle("-fx-text-fill: #463634;");
        secondMenuDescription.setStyle("-fx-text-fill: #463634;");
        secondMenuPrice.setStyle("-fx-text-fill: #463634;");
        secondMenuEditButton.setStyle("-fx-background-radius: 5;-fx-padding: 5 62;");
        secondMenuDeleteButton.setStyle("-fx-background-radius: 5;-fx-padding: 5 50;");

        insertNewMenuContainer.setMaxHeight(190);
        insertNewMenuContainer.setMaxWidth(150);
        insertNewMenuDescription.setWrappingWidth(200);
        Border insertNewMenuBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        insertNewMenuContainer.setBorder(insertNewMenuBorder);
        insertNewMenuContainer.setPadding(new Insets(20, 20, 20, 20));
        BaseHBox.setMargin(insertNewMenuContainer, new Insets(10,10,0,10));
        insertNewMenuContainer.setStyle("-fx-text-fill: #463634;");
        insertNewMenuContainer.setStyle("-fx-text-fill: #463634;");
        insertNewMenuContainer.setStyle("-fx-text-fill: #463634;");
        insertNewMenuItemButtonButtom.setStyle("-fx-background-radius: 5;-fx-padding: 5 40;");
    }

    /**
     * Attach any event listeners to your control element of choice, here.
     * <br></br>
     * These event listeners serve to determine what action does a particular element is expected to do when interacted with.
     */
    public void initializeEventListeners()
    {
        
    }

    /**
     * Attach your declared-and-configured control elements to your root component here.
     */
    public void assembleLayout()
    {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle, 
            insertNewMenuItemButtonTop
        );

        firstMenuContainer.getChildren().addAll(
            firstMenuTitle,
            firstMenuDescription,
            firstMenuPrice,
            firstMenuEditButton,
            firstMenuDeleteButton
        );

        

        secondMenuContainer.getChildren().addAll(
            secondMenuTitle,
            secondMenuDescription,
            secondMenuPrice,
            secondMenuEditButton,
            secondMenuDeleteButton
        );

        insertNewMenuContainer.getChildren().addAll(
            insertNewMenuTitle,
            insertNewMenuDescription,
            insertNewMenuItemButtonButtom
        );

        pageContent.getChildren().addAll(
            firstMenuContainer,
            secondMenuContainer,
            insertNewMenuContainer 
        );

        container.getChildren().addAll(
            pageIdentifierContainer,
            pageContent
        );

        rootElement.getChildren().addAll(
            container
        );
    }

    /**
     * Set up your scene's root element here.
     * Idealy, there can only be ONE root element for any given scene.
     */
    public void setupScene()
    {
        setCenter(scrollSupport);
    }
}