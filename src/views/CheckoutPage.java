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
import models.MenuItem;
import models.OrderItem;
import models.Order.OrderStatus;
import models.User;
import views.components.buttons.BaseButton;
import views.components.buttons.CTAButton;
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

public class CheckoutPage extends BorderPane implements PageDeclarationGuideline_v1{

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
    private Button confirmOrderBtn;


    private BaseNumberfieldBuilder baseNumberfieldBuilder;
    private BaseNumberfield baseNumberfieldObj;

    private ArrayList<MenuItem> menuItems_checkout;
    private ArrayList<Integer> quantity_checkout;

    private ArrayList<BaseCardView> cardViews;

    private Double tempTotalPrice;


    public CheckoutPage () {
        menuItems_checkout = getCheckoutItems(App.PASSING_ITEM_ORDER_CHANNEL_FOR_CHECKOUT);
        quantity_checkout  = getCheckoutItems(App.PASSING_ORDER_QUANTITY_CHANNEL_FOR_CHECKOUT);

        initializeScene();
    }

    @Override
    public void initializeControls() {
        rootElement   = new RootElement();
        container     = new Container().centerContentHorizontally();
        scrollSupport = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox();
            backBtn             = new BaseButton("Back");
            pageTitle           = new H1Label("Checkout Menu").withBoldFont();

        pageContent = new BaseVBox();
            topPageContent = new BaseHBox();
                toplabel = new H5Label("Detail Pesanan");

            content = new BaseVBox();
                cardViews = new ArrayList<>();
                tempTotalPrice = 0d;
                for(MenuItem menuItem : menuItems_checkout){
                    Integer quantity = quantity_checkout.get(menuItems_checkout.indexOf(menuItem));
                    BaseCardView cardView = new BaseCardView();

                    BorderPane orderItemView = new BorderPane();
                            HBox orderItemContent = new BaseHBox();
                                Label menuName = new H5Label(menuItem.getMenuItemName());
                                Label menuPrice = new H5Label("Rp"+Integer.toString(menuItem.getMenuItemPrice()));

                                baseNumberfieldBuilder = new BaseNumberfieldBuilder()
                                                                .withMaximumValueOf(10)
                                                                .withMinimumValueOf(0)
                                                                .withInitialValueOf(quantity);
                                baseNumberfieldObj = baseNumberfieldBuilder.build();

                    orderItemContent.getChildren().addAll(
                        menuName,
                        menuPrice
                    );

                    orderItemView.setLeft(orderItemContent);
                    orderItemView.setRight(baseNumberfieldObj);

                    cardView.setContent(orderItemView);
                    cardViews.add(cardView);

                    tempTotalPrice = tempTotalPrice + menuItem.getMenuItemPrice() * quantity;
                }

            bottomPageContent = new BorderPane();
                totalLabel = new H3Label("Total").withBoldFont();
                totalPrice = new H3Label("Rp"+String.format("%.2f", tempTotalPrice));

        buttonContainer = new BaseHBox();
            confirmOrderBtn = new CTAButton("Confirm Order");
    }

    @Override
    public void configureElements() {

    }

    @Override
    public void initializeEventListeners() {
        backBtn.setOnMouseClicked(e ->{
            App.redirectTo(
                App.sceneBuilder( new CustomerDashboardPage() )
            );
        });

        confirmOrderBtn.setOnMouseClicked(e -> {
            OrderController orderController = new OrderController();
            OrderItemController OrderItemController = new OrderItemController();

            User user = (User) App.preferences.getValue(App.CURRENT_USER_KEY);
            Integer orderId = orderController.post(user , OrderStatus.PENDING, getDateTime(), tempTotalPrice).getOrderId();

            for (MenuItem menuItem : menuItems_checkout) {
                OrderItem oi = new OrderItem();
                oi.setMenuItem(menuItem);
                oi.setOrderId(orderId);
                oi.setQuantity(quantity_checkout.get(menuItems_checkout.indexOf(menuItem)));

                OrderItemController.post(oi);
            }

            App.redirectTo(App.sceneBuilder(new CustomerDashboardPage()));
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
            confirmOrderBtn
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

    private <T> ArrayList<T> getCheckoutItems(String channel) {
        Object rawItems = App.preferences.getValue(channel);

        if (rawItems instanceof ArrayList<?>) {
            @SuppressWarnings("unchecked")
            ArrayList<T> checkoutItems = (ArrayList<T>) rawItems;
            return checkoutItems;
        } else {
            return new ArrayList<>();
        }
    }

    public static String getDateTime() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        String formattedDateTime = currentTimestamp.toString();

        return formattedDateTime;
    }
}
