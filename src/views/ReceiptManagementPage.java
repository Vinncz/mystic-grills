package views;

import java.util.ArrayList;

import application_starter.App;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Receipt;
import repositories.ReceiptRepository;
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

public class ReceiptManagementPage extends BorderPane implements PageDeclarationGuideline_v1 {

    public ArrayList<Receipt> receipts;

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
    private BaseButton backButton;
    private Label pageTitle;

    private BaseFlowpane pageContent;
    private ArrayList<BaseCardView> cardViews;
    private ArrayList<BaseButton> buttonsForCardViews;

    public ReceiptManagementPage () {
        ReceiptRepository rere = new ReceiptRepository();
        this.receipts = rere.getAll();
        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement = new RootElement();
        container = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox().withNoSpacing();
            backButton = new BaseButton("Back");
            pageTitle = new H2Label("Receipt Management Studio").withExtraBoldFont();

        pageContent = new BaseFlowpane().growsHorizontally().growsVertically();
            buttonsForCardViews = new ArrayList<>();
            cardViews = new ArrayList<>();

            receipts.forEach(receipt -> {
                BaseButton b = new BaseButton("Check this receipt").withSizeOf(18);
                b.setOnMouseClicked(e -> {
                    App.preferences.putValue(App.PASSING_ID_CHANNEL_FOR_RECEIPT_DETAIL_PAGE, receipt.getReceiptId());
                    App.redirectTo( App.sceneBuilder( new ReceiptDetailPage() ) );
                });
                buttonsForCardViews.add(b);

                cardViews.add(
                    new BaseCardView()
                            .setContent(
                                new H4Label("Receipt #" + receipt.getReceiptId()).withExtraBoldFont(),
                                new H5Label("Ordered by\n" + receipt.getReceiptOrder().getOrderUser().getUserName()),
                                b
                            ).growsVertically()
                );
            });
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
        backButton.setStyle("-fx-background-radius: 8;");
    }

    @Override
    public void initializeEventListeners() {
        backButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder( new CashierDashboardPage() ) );
        });
    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            backButton,
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
