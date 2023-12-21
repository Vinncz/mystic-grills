package views;

import java.sql.Timestamp;
import java.util.ArrayList;

import application_starter.App;
import controllers.OrderController;
import controllers.OrderItemController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Order;
import models.OrderItem;
import views.components.buttons.BaseButton;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.card_views.BaseCardView;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H3Label;
import views.components.labels.H5Label;
import views.components.number_inputs.BaseNumberfield;
import views.components.number_inputs.BaseNumberfieldBuilder;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class ViewOrderMenuPageCustomer extends BorderPane implements PageDeclarationGuideline_v1{

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private Button backBtn;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private VBox pageContent;
    private HBox topPageContent;
    private VBox content;
    private BorderPane bottomPageContent;

    private Label totalLabel , totalPrice , toplabel;

    private HBox buttonContainer;
    private Button confirmChangeBtn , discardChangeBtn;

    private ArrayList<OrderItem> orderItems;

    private ArrayList<BaseCardView> cardViews;
    private ArrayList<BaseNumberfield> baseNumberfields;

    private Double tempTotalPrice;
    private Integer orderId;

    public ViewOrderMenuPageCustomer() {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        orderId = (Integer) App.preferences.getValue(App.PASSING_ORDER_CHANNEL_FOR_CHECK_ORDER_DETAIL);

        OrderItemController orderItemController = new OrderItemController();

        orderItems = orderItemController.getByOrderId(orderId);


        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox();
            backBtn = new BaseButton("Back");
            pageTitle = new H1Label("View Ordered Menu").withBoldFont();

        pageContent = new BaseVBox();
            topPageContent = new BaseHBox();
                toplabel = new H5Label("Detail Pesanan");

            content = new BaseVBox();
                cardViews = new ArrayList<>();
                    baseNumberfields = new ArrayList<>();

                tempTotalPrice = 0d;

                for(OrderItem orderItem : orderItems){

                    BaseCardView cardView = new BaseCardView();

                    BorderPane orderItemView = new BorderPane();

                            HBox orderItemContent = new BaseHBox();
                                Label menuName = new H5Label(orderItem.getMenuItem().getMenuItemName());
                                Label menuPrice = new H5Label("$"+Integer.toString(orderItem.getMenuItem().getMenuItemPrice()));

                                BaseNumberfieldBuilder baseNumberfieldBuilder = new BaseNumberfieldBuilder()
                                                                .withMaximumValueOf(1000)
                                                                .withMinimumValueOf(0)
                                                                .withInitialValueOf(orderItem.getQuantity());
                                BaseNumberfield baseNumberfieldObj = baseNumberfieldBuilder.build();

                                baseNumberfields.add(baseNumberfieldObj);

                    orderItemContent.getChildren().addAll(
                        menuName,
                        menuPrice
                    );


                    orderItemView.setLeft(orderItemContent);
                    orderItemView.setRight(baseNumberfieldObj);

                    cardView.setContent(orderItemView);
                    cardViews.add(cardView);


                    tempTotalPrice = tempTotalPrice + orderItem.getMenuItem().getMenuItemPrice() * orderItem.getQuantity();
                }

            bottomPageContent = new BorderPane();
                totalLabel = new H3Label("Total").withBoldFont();
                totalPrice = new H3Label("$"+String.format("%.2f", tempTotalPrice));

        buttonContainer = new BaseHBox();
            confirmChangeBtn = new CTAButton("Confirm Order");
            discardChangeBtn = new DestructiveButton("Discard Change");
    }

    @Override
    public void configureElements() {

    }

    @Override
    public void initializeEventListeners() {

        backBtn.setOnMouseClicked(e ->{
            App.redirectTo(App.sceneBuilder(new MyOrderPageCustomer()));
        });

        discardChangeBtn.setOnMouseClicked(e -> {
            App.redirectTo(App.sceneBuilder(new MyOrderPageCustomer()));
        });

        confirmChangeBtn.setOnMouseClicked(e -> {
            OrderItemController orderItemController = new OrderItemController();
            OrderController orderController = new OrderController();

            Order currentOrder = orderController.getById(orderId).get();

            tempTotalPrice = 0d;

            for (OrderItem  orderItem : orderItems) {
                Integer updatedQuantity = Integer.parseInt(baseNumberfields.get(orderItems.indexOf(orderItem)).getInputField().getText());
                Integer orderItemPrice = orderItem.getMenuItem().getMenuItemPrice();

                tempTotalPrice = tempTotalPrice + updatedQuantity * orderItemPrice  ;
                System.out.println("total:" + tempTotalPrice);

                if(updatedQuantity == 0){
                    orderItemController.delete(orderItem.getOrderItemId());
                }
                else{
                    orderItemController.put(orderItem.getOrderItemId(),orderItem.getOrderId(),orderItem.getMenuItem(),updatedQuantity);
                }

            }

            orderController.put(orderId, currentOrder.getOrderUser(), currentOrder.getOrderStatus(), currentOrder.getOrderDate() , tempTotalPrice);

            App.redirectTo(App.sceneBuilder(new MyOrderPageCustomer()));
        });

    }

    @Override
    public void assembleLayout() {

        pageIdentifierContainer.getChildren().addAll(
            backBtn,
            pageTitle
        );

        topPageContent.getChildren().addAll(
            toplabel
        );

        for(BaseCardView cardView : cardViews){
            content.getChildren().add(
                cardView.get()
            );
        }

        buttonContainer.getChildren().addAll(
            discardChangeBtn,
            confirmChangeBtn
        );

        bottomPageContent.setLeft(totalLabel);
        bottomPageContent.setRight(totalPrice);

        pageContent.getChildren().addAll(
            topPageContent,
            content,
            bottomPageContent
        );

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

    public static String getDateTime() {

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        String formattedDateTime = currentTimestamp.toString();

        return formattedDateTime;
    }
}
