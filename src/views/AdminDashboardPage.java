package views;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import application_starter.App;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import views.components.buttons.BaseButton;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.guidelines.PageDeclarationGuideline_v1;

public class AdminDashboardPage extends BorderPane implements PageDeclarationGuideline_v1{

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private HBox pageContent;
    private VBox menuManagementContainer, userManagementContainer;

    private Label menuManagementLabel, userManagementLabel;
    private Button menuManagementButton, userManagementButton;
    private Label menuManagementDescLabel, userManagementDescLabel;

    public AdminDashboardPage()
    {
        initializeScene();
    }

    /**
     * Initialize your scene's various elements here.
     */
    @Override
    public void initializeControls()
    {
        rootElement = new RootElement();
        container = new Container();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox().withVeryLooseSpacing();
            pageTitle = new H1Label("Admin Dashboard").withBoldFont().withAlternateFont();

        pageContent = new BaseHBox().withCustomSpacing(200);
            menuManagementContainer = new BaseVBox().withTightSpacing().centerContentVertically();
                menuManagementLabel = new H3Label("Menu Item Management Studio").withBoldFont().withAlternateFont();
                menuManagementDescLabel = new H5Label("Simply click this button to check menu item management");
                menuManagementButton = new BaseButton("Take me there");

            userManagementContainer = new BaseVBox().withTightSpacing();
                userManagementLabel = new H3Label("User Management Studio").withBoldFont().withAlternateFont();
                userManagementDescLabel = new H5Label("Simply click this button to check user management");
                userManagementButton = new BaseButton("Take me there");

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
    @Override
    public void configureElements()
    {
        // pageContent.setMaxWidth(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value));
        pageContent.setPrefWidth(800);
        pageContent.setPrefHeight(500);
        pageContent.getStyleClass().addAll("py-16");
        pageContent.setSpacing(24);
        pageContent.setStyle("-fx-background-color: #FFFFFF;");
        Border pageContentBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        pageContent.setBorder(pageContentBorder);


        //menu management container
        menuManagementContainer.setMaxHeight(200);
        Border menuBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        menuManagementContainer.setBorder(menuBorder);
        menuManagementContainer.setPadding(new Insets(20, 20, 20, 20));
        BaseHBox.setMargin(menuManagementContainer, new Insets(10,20,0,30));

        //menu management label
        menuManagementLabel.setStyle("-fx-text-fill: #463634;");

        //menu management description label
        menuManagementDescLabel.setStyle("-fx-text-fill: #463634;");

        //menu management button
        BaseVBox.setMargin(menuManagementButton, new Insets(30,0,0,0));
        menuManagementButton.setStyle("-fx-background-color: #463634; -fx-text-fill: #ffffff; -fx-background-radius: 5;");


        //user management container
        userManagementContainer.setMaxHeight(200);
        Border userBorder = new Border(new javafx.scene.layout.BorderStroke(Color.valueOf("#C7B2AF"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2)));
        userManagementContainer.setBorder(userBorder);
        userManagementContainer.setPadding(new Insets(20, 20, 20, 20));
        BaseHBox.setMargin(userManagementContainer, new Insets(10,0,0,20));

        //user management label
        userManagementLabel.setStyle("-fx-text-fill: #463634;");

        //user management description label
        userManagementDescLabel.setStyle("-fx-text-fill: #463634;");

        //user management button
        BaseVBox.setMargin(userManagementButton, new Insets(30,0,0,0));
        userManagementButton.setStyle("-fx-background-color: #463634; -fx-text-fill: #ffffff; -fx-background-radius: 5;");

    }

    /**
     * Attach any event listeners to your control element of choice, here.
     * <br></br>
     * These event listeners serve to determine what action does a particular element is expected to do when interacted with.
     */
    @Override
    public void initializeEventListeners()
    {
        menuManagementButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new MenuItemManagementPage()) );
        });

        menuManagementButton.setOnMouseEntered(e -> {
            menuManagementButton.setStyle("-fx-background-color: #7E615D; -fx-text-fill: #ffffff;-fx-background-radius: 5;");
        });

        menuManagementButton.setOnMouseExited(e -> {
            menuManagementButton.setStyle("-fx-background-color: #463634; -fx-text-fill: #ffffff;-fx-background-radius: 5;");
        });

        userManagementButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder(new UserManagementPage()) );
        });

        userManagementButton.setOnMouseEntered(e -> {
            userManagementButton.setStyle("-fx-background-color: #7E615D; -fx-text-fill: #ffffff; -fx-background-radius: 5;");
        });

        userManagementButton.setOnMouseExited(e -> {
            userManagementButton.setStyle("-fx-background-color: #463634; -fx-text-fill: #ffffff; -fx-background-radius: 5;");
        });
    }

    /**
     * Attach your declared-and-configured control elements to your root component here.
     */
    @Override
    public void assembleLayout()
    {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle
        );

        menuManagementContainer.getChildren().addAll(
            menuManagementLabel,
            menuManagementDescLabel,
            menuManagementButton
        );

        userManagementContainer.getChildren().addAll(
            userManagementLabel,
            userManagementDescLabel,
            userManagementButton
        );

        pageContent.getChildren().addAll(
            menuManagementContainer,
            userManagementContainer
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
    @Override
    public void setupScene()
    {
        setCenter(scrollSupport);
    }


}
