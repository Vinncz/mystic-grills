package views;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application_starter.App;
import design_patterns.observer_pattern.Observer;
import design_patterns.strategy_pattern.TextfieldValidationStrategy;
import design_patterns.strategy_pattern.VanishingLabelValidationStrategy;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Order;
import models.OrderItem;
import models.Receipt;
import repositories.OrderRepository;
import repositories.ReceiptRepository;
import values.strings.ValidationState;
import views.components.buttons.CTAButton;
import views.components.combo_boxes.BaseComboBoxBuilder;
import views.components.combo_boxes.ComboBoxData;

import views.components.hboxes.RootElement;
import views.components.labels.H2Label;
import views.components.labels.H3Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.number_inputs.BaseNumberfield;
import views.components.number_inputs.BaseNumberfieldBuilder;
import views.components.scroll_panes.BaseScrollPane;
import views.components.textfields.DefaultTextfield;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class OrderDetailPage extends BorderPane implements PageDeclarationGuideline_v1 {

    private Order passedOnOrderData;
    private ArrayList<ValidationState> errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded = new ArrayList<>() {
        {
            add(ValidationState.AMOUNT_PAID_IS_LOWER_THAN_WHATS_NEEDED);
        }
    };

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private VBox pageIdentifierContainer;
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

    private VBox paymentOptionsContainer;
        private ArrayList<ComboBoxData> paymentMethods = new ArrayList<>() {{
            add(new ComboBoxData("Cash", Receipt.ReceiptPaymentType.CASH));
            add(new ComboBoxData("Credit", Receipt.ReceiptPaymentType.CREDIT));
            add(new ComboBoxData("Debit", Receipt.ReceiptPaymentType.DEBIT));
        }};
        private Label paymentMethodHandle;
        private BaseComboBoxBuilder comBoxBuilder;
        private ComboBox<ComboBoxData> comBoxObj;
        private VBox amountToBePaidContainer;
            private Label amountToBePaidHandle;
            private BaseNumberfieldBuilder numberInputBuilder;
            private BaseNumberfield numberInputObj;
            private Label numberInputWarnLabel;
            private Button saveButton;

    public OrderDetailPage () {
        // int passedOnOrderId = (int) App.preferences.getValue("");
        int passedOnOrderId = 9;

        OrderRepository orderRepo = new OrderRepository();
        if ( orderRepo.getById(passedOnOrderId).isPresent() )
            passedOnOrderData = orderRepo.getById(passedOnOrderId).get();
        else {
            System.out.println("NO ORDER_ID CAN BE FETCHED FOR ORDER DETAIL PAGE!");
            // balikin user ke halaman sebelumnya
            return;
        }

        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement = new RootElement();
        container = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox().withNoSpacing();
            pageTitle = new H2Label("Order Detail").withExtraBoldFont();

        pageContent = new BaseVBox().growsHorizontally();
            customerInfoContainer = new BaseVBox().growsHorizontally();
                customerNameHandle = new H4Label("Customer that ordered: ").withRegularFont();
                customerName = new H3Label(passedOnOrderData.getOrderUser().getUserName()).withBlackFont();

            orderDetailContainer = new BaseVBox().growsHorizontally();
                orderDetailHandle = new H4Label("Ordered menu").withRegularFont();

                orderDetailRowContainer = new ArrayList<>();
                quantityForMenuItem = new ArrayList<>();
                menuItemName = new ArrayList<>();
                priceSubTotalForMenuItem = new ArrayList<>();
                for (OrderItem orderedItem : passedOnOrderData.getOrderItems()) {
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
                    totalPrice = new H3Label("$" + passedOnOrderData.getOrderTotal().toString()).withBlackFont();

            paymentOptionsContainer = new BaseVBox();
                paymentMethodHandle = new H4Label("Pick your payment method").withRegularFont();
                comBoxBuilder = new BaseComboBoxBuilder().withDataOf(paymentMethods).withInitialValueOf(paymentMethods.get(0));
                comBoxObj = comBoxBuilder.build();
                amountToBePaidContainer = new BaseVBox().withTightSpacing();
                    amountToBePaidHandle = new H4Label("Amount to be paid");
                    numberInputBuilder = new BaseNumberfieldBuilder().withInitialValueOf(0).withMaximumValueOf(Integer.MAX_VALUE).withMinimumValueOf(0);
                    numberInputObj = numberInputBuilder.build();
                    numberInputWarnLabel = new H5Label("warn").setStrategy(new VanishingLabelValidationStrategy().setRegisteredErrorWatchList(errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded));
                    saveButton = new CTAButton("Process order").withBoldFont();
    }

    @Override
    public void configureElements() {
        ((DefaultTextfield)numberInputObj.getInputField()).setStrategy(new TextfieldValidationStrategy()
                                                .setRegisteredErrorWatchList(errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded));
        ArrayList<Object> _errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded = new ArrayList<>(errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded.stream().map(ValidationState::value).collect(Collectors.toList()));

        App.preferences.subscribeToMany(
            _errorToWatchSoThatAmountPaidCannotBeLowerThanWhatsNeeded,

            (Observer) numberInputObj.getInputField(),
            (Observer) numberInputWarnLabel
        );

        // customerInfoContainer.setStyle("-fx-margin: 48 0 0 0; -fx-padding: 24; -fx-border-color: -primary-gray; -fx-border-width: 1px;");
        customerInfoContainer.setStyle("-fx-margin: 48 0 0 0;");
        customerInfoContainer.getStyleClass().add("cardView");
        orderDetailContainer.getStyleClass().add("cardView");
        paymentOptionsContainer.getStyleClass().add("cardView");

        amountToBePaidContainer.setAlignment(Pos.BASELINE_LEFT);
        amountToBePaidContainer.setStyle("-fx-padding: 0 0 36 0;");
        numberInputObj.setAlignment(Pos.BASELINE_LEFT);
        numberInputWarnLabel.setPadding(new Insets(16, 0, 0, 0));
        numberInputWarnLabel.setVisible(false);
        numberInputWarnLabel.setManaged(false);

        pageContent.setStyle("-fx-padding: 0 0 108 0");
    }

    @Override
    public void initializeEventListeners() {
        numberInputObj.setOnMouseClicked(e -> {
            App.preferences.putValue(ValidationState.AMOUNT_PAID_IS_LOWER_THAN_WHATS_NEEDED.value, false);
        });
        numberInputObj.getInputField().setOnMouseClicked(e -> {
            App.preferences.putValue(ValidationState.AMOUNT_PAID_IS_LOWER_THAN_WHATS_NEEDED.value, false);
        });
        saveButton.setOnMouseClicked(e -> {
            Integer toBePaidAmount = Integer.parseInt(numberInputObj.getInputField().getText());
            if ( passedOnOrderData.getOrderTotal() > toBePaidAmount ) {
                App.preferences.putValue(ValidationState.AMOUNT_PAID_IS_LOWER_THAN_WHATS_NEEDED.value, true);
                return;
            }

            App.preferences.putValue(ValidationState.AMOUNT_PAID_IS_LOWER_THAN_WHATS_NEEDED.value, false);
            Receipt r = new Receipt();
            r.setReceiptOrder(passedOnOrderData);
            r.setReceiptAmountPaid((double) toBePaidAmount);
            r.setReceiptPaymentType((Receipt.ReceiptPaymentType) comBoxBuilder.getSelectedData());

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            r.setReceiptPaymentDate( currentDateTime.format(formatter) );

            ReceiptRepository repo = new ReceiptRepository();
            r = repo.post(r);

            App.redirectTo(App.sceneBuilder(new LoginPage()));
        });
    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            pageTitle
        );

        customerInfoContainer.getChildren().addAll(
            customerNameHandle,
            customerName
        );

        for (int i = 0; i < passedOnOrderData.getOrderItems().size(); i++) {
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

        amountToBePaidContainer.getChildren().addAll(
            amountToBePaidHandle,
            numberInputObj,
            numberInputWarnLabel
        );

        paymentOptionsContainer.getChildren().addAll(
            paymentMethodHandle,
            comBoxObj,
            amountToBePaidContainer,
            saveButton
        );

        pageContent.getChildren().addAll(
            customerInfoContainer,
            orderDetailContainer,
            paymentOptionsContainer
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
