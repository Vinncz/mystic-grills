package views;

import java.util.ArrayList;

import application_starter.App;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.OrderItem;
import models.Receipt;
import repositories.ReceiptRepository;
import views.components.buttons.BaseButton;
import views.components.hboxes.RootElement;
import views.components.labels.H2Label;
import views.components.labels.H3Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class ReceiptDetailPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private Receipt data;

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
        private BaseButton backButton;
        private Label pageTitle;

    private VBox pageContent;
    private VBox customerInfoContainer;
        private Label customerNameHandle;
        private Label customerName;

    private VBox orderDetailContainer;
        private Label orderDetailHandle;
        private ArrayList<GridPane> orderDetailRowContainer;
            private ArrayList<Label> quantityForMenuItem;
            private ArrayList<Label> menuItemName;
            private ArrayList<Label> priceSubTotalForMenuItem;

        private VBox orderTotalContainer;
            private Label totalHandle;
            private Label totalPrice;

    public ReceiptDetailPage () {
        int receiptId = (int) App.preferences.getValue(App.PASSING_ID_CHANNEL_FOR_RECEIPT_DETAIL_PAGE);
        ReceiptRepository rere = new ReceiptRepository();

        data = rere.getById(receiptId).get();

        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement = new RootElement();
        container = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox().withNoSpacing();
            backButton = new BaseButton("Back");
            pageTitle = new H2Label("Receipt Detail").withExtraBoldFont();

        pageContent = new BaseVBox().growsHorizontally();
            customerInfoContainer = new BaseVBox().growsHorizontally();
                customerNameHandle = new H4Label("Customer that ordered: ").withRegularFont();
                customerName = new H3Label(data.getReceiptOrder().getOrderUser().getUserName()).withBlackFont();

            orderDetailContainer = new BaseVBox().growsHorizontally();
                orderDetailHandle = new H4Label("Ordered menu").withRegularFont();

                orderDetailRowContainer = new ArrayList<>();
                quantityForMenuItem = new ArrayList<>();
                menuItemName = new ArrayList<>();
                priceSubTotalForMenuItem = new ArrayList<>();
                for (OrderItem orderedItem : data.getReceiptOrder().getOrderItems()) {
                    Label quantity = new H5Label(orderedItem.getQuantity().toString() + "x").withRegularFont();
                    Label itemName = new H5Label(
                        String.format("%s", orderedItem.getMenuItem().getMenuItemName())
                    ).withRegularFont();
                    Label itemPrice = new H5Label(String.format("%18d", orderedItem.getQuantity() * orderedItem.getMenuItem().getMenuItemPrice())).withRegularFont();

                    HBox.setHgrow(quantity, Priority.ALWAYS);
                    HBox.setHgrow(itemName, Priority.ALWAYS);
                    HBox.setHgrow(itemPrice, Priority.ALWAYS);

                    quantityForMenuItem.add(quantity);
                    menuItemName.add(itemName);
                    priceSubTotalForMenuItem.add(itemPrice);
                }

                orderTotalContainer = new BaseVBox().growsHorizontally();
                    totalHandle = new H4Label("Amount to be paid").withRegularFont();
                    totalPrice = new H3Label("$" + data.getReceiptOrder().getOrderTotal().toString()).withBlackFont();
    }

    @Override
    public void configureElements() {
        customerInfoContainer.setStyle("-fx-margin: 48 0 0 0;");
        customerInfoContainer.getStyleClass().add("cardView");
        orderDetailContainer.getStyleClass().add("cardView");
        pageContent.setStyle("-fx-padding: 0 0 108 0");

        backButton.setStyle("-fx-background-radius: 8;");
    }

    @Override
    public void initializeEventListeners() {
        backButton.setOnMouseClicked(e -> {
            App.redirectTo( App.sceneBuilder( new ReceiptManagementPage() ) );
        });
    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            backButton,
            pageTitle
        );

        customerInfoContainer.getChildren().addAll(
            customerNameHandle,
            customerName
        );

        for (int i = 0; i < data.getReceiptOrder().getOrderItems().size(); i++) {
            GridPane row = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);

            row.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
            row.addColumn(0, quantityForMenuItem.get(i));
            row.addColumn(1, menuItemName.get(i));
            row.addColumn(2, priceSubTotalForMenuItem.get(i));

            orderDetailRowContainer.add(
                row
            );

        }

        orderTotalContainer.getChildren().addAll(
            totalHandle,
            totalPrice
        );

        orderDetailContainer.getChildren().addAll(
            orderDetailHandle
        );

        orderDetailRowContainer.forEach(rowContainer -> {
            orderDetailContainer.getChildren().add(rowContainer);
        });

        orderDetailContainer.getChildren().addAll(
            orderTotalContainer
        );

        pageContent.getChildren().addAll(
            customerInfoContainer,
            orderDetailContainer
        );

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
