package views;

import java.util.ArrayList;

import application_starter.App;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import views.components.buttons.BaseButton;
import views.components.card_views.BaseCardView;
import views.components.flowpanes.BaseFlowpane;
import views.components.hboxes.RootElement;
import views.components.labels.H2Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class CashierDashboardPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private BaseFlowpane pageContent;
    private ArrayList<BaseCardView> cardViews;
    private ArrayList<BaseButton> buttonsForCardViews;

    public CashierDashboardPage () {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement = new RootElement();
        container = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox().withNoSpacing();
            pageTitle = new H2Label("Cashier Dashboard").withExtraBoldFont();

        pageContent = new BaseFlowpane().growsHorizontally().growsVertically();
            buttonsForCardViews = new ArrayList<>() {{
                add(new BaseButton("Take me there").withSizeOf(18));
                add(new BaseButton("Take me there").withSizeOf(18));
            }};
            cardViews = new ArrayList<>(){{
                add(new BaseCardView()
                            .setContent(
                                new H4Label("Order Management").withExtraBoldFont(),
                                new H5Label("Access and manage resto's order").withRegularFont(),
                                buttonsForCardViews.get(0)
                            ).growsVertically()
                );
                add(new BaseCardView()
                            .setContent(
                                new H4Label("Receipt Management").withExtraBoldFont(),
                                new H5Label("Access and manage resto's receipt").withRegularFont(),
                                buttonsForCardViews.get(1)
                            ).growsVertically()
                );
            }};
    }

    @Override
    public void configureElements() {
        buttonsForCardViews.forEach(button -> {
            button.setStyle("-fx-background-radius: 4;");
            button.getStyleClass().addAll("editButton");
        });
        cardViews.forEach(card -> {
            card.getInstance().growsVertically();
            card.getInstance().getStyleClass().addAll("-fx-background-color: -primary-red");
        });
    }

    @Override
    public void initializeEventListeners() {
        buttonsForCardViews.get(0).setOnMouseClicked(e -> {
            App.redirectTo(App.sceneBuilder(new LoginPage()));
        });
        buttonsForCardViews.get(1).setOnMouseClicked(e -> {
            App.redirectTo(App.sceneBuilder(new RegisterPage()));
        });
    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle
        );

        for ( BaseCardView bcv : cardViews ) {
            pageContent.getChildren().add(bcv.get());
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
