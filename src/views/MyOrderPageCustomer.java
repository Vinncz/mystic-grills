package views;

import java.util.ArrayList;

import application_starter.App;
import controllers.OrderController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import models.Order;
import models.User;
import views.components.buttons.BaseButton;
import views.components.card_views.BaseCardView;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class MyOrderPageCustomer extends BorderPane implements PageDeclarationGuideline_v1{

    private ScrollPane scrollSupport;
    private HBox rootElement;
    private VBox container;

    private Button backBtn;

    private VBox pageIdentifierContainer;
    private Label pageTitle;

    private FlowPane pageContent;

    private ArrayList<BaseCardView> cardViews;
    private ArrayList<Order> orders;
    private ArrayList<Button> checkOrderBtns;

    public MyOrderPageCustomer () {
        initializeScene();
    }

    @Override
    public void initializeControls() {
        User currentlyLoggedUser = (User) App.preferences.getValue(App.CURRENT_USER_KEY);

        OrderController orderController = new OrderController();
        orders = orderController.getByUserId(currentlyLoggedUser.getUserId());

        rootElement    = new RootElement();
        container      = new Container().centerContentHorizontally();
        scrollSupport  = new BaseScrollPane(rootElement);

        pageIdentifierContainer = new BaseVBox();
            backBtn = new BaseButton("Back");
            pageTitle = new H1Label("My Orders").withBlackFont();

        pageContent = new FlowPane();
            cardViews = new ArrayList<>();
                checkOrderBtns = new ArrayList<>();

                for (Order order : orders){
                    BaseCardView cardView = new BaseCardView();

                        VBox content1 = new BaseVBox().withTightSpacing();
                            Label nomorOrderLabel = new H5Label("Nomor Order").withLightFont();
                            Label nomorOrder = new H4Label(order.getOrderId().toString()).withBoldFont();

                        HBox linePane = new BaseHBox();
                            Line line = new Line(0, 0, 350, 0);

                        VBox content2 = new BaseVBox().withTightSpacing();
                            Label totalLabel = new H5Label("Total").withLightFont();
                            Label total = new H4Label("$"+String.format("%.2f", order.getOrderTotal())).withBoldFont();

                        HBox btnContent = new BaseHBox();
                            Button checkOrder = new BaseButton("Check Order");

                    line.prefWidth(100);
                    line.setStyle("-fx-stroke: #C5C5C5;");

                    content1.getChildren().addAll(
                        nomorOrderLabel,
                        nomorOrder
                    );

                    linePane.getChildren().addAll(
                        line
                    );

                    content2.getChildren().addAll(
                        totalLabel,
                        total
                    );

                    btnContent.getChildren().addAll(
                        checkOrder
                    );

                    cardView.setContent(
                        content1,
                        linePane,
                        content2,
                        btnContent
                    );

                    checkOrderBtns.add(checkOrder);
                    cardViews.add(cardView);
                }


    }

    @Override
    public void configureElements() {
        pageContent.setVgap(20);
        pageContent.setHgap(20);
    }

    @Override
    public void initializeEventListeners() {

        backBtn.setOnMouseClicked(e ->{
            App.redirectTo(App.sceneBuilder(new CustomerDashboardPage()));
        });

        for(Button checkOrderBtn : checkOrderBtns){
            checkOrderBtn.setOnMouseClicked(e -> {
                Integer index = checkOrderBtns.indexOf(checkOrderBtn);
                App.preferences.putValue(App.PASSING_ORDER_CHANNEL_FOR_CHECK_ORDER_DETAIL, orders.get(index).getOrderId());
                App.redirectTo(App.sceneBuilder(new ViewOrderMenuPageCustomer()));

            });
        }


    }

    @Override
    public void assembleLayout() {
        pageIdentifierContainer.getChildren().addAll(
            backBtn,
            pageTitle
        );

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
