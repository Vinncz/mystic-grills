package views;

import java.util.ArrayList;

import application_starter.App;
import controllers.OrderController;
import controllers.OrderItemController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Order;
import models.Order.OrderStatus;
import models.OrderItem;
import models.User;
import models.User.UserRole;
import repositories.OrderRepositoryProxy.OrderClassifiedByStatusReturnDatatype;
import views.components.buttons.OutlineButton;
import views.components.card_views.BaseCardView;
import views.components.flowpanes.BaseFlowpane;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H2Label;
import views.components.labels.H3Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.labels.HLabel;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class ViewOrderPage extends BorderPane implements PageDeclarationGuideline_v1 {

	private ScrollPane scrollSupport;
	private HBox rootElements;
	private VBox container;
	
	private VBox pageIdentifierContainer, orderContainer, pageContent;
	private FlowPane orderHolder;
	private Label pageTitle;
	
	private HBox backButtonContainer;
	private Button backButton;
	
	private OrderController oc; 
	private OrderClassifiedByStatusReturnDatatype orderType;
	private ArrayList<Order> orders;
	private User user;
	
	private BaseCardView orderCardView;
	
	public ViewOrderPage() {
		initializeScene();
	}
	
	@Override
	public void initializeControls() {
		rootElements = new RootElement();
		container = new Container().centerContentHorizontally();
		scrollSupport = new BaseScrollPane(rootElements);
		
		pageIdentifierContainer = new BaseVBox().withNormalSpacing().centerContentHorizontally();
			pageTitle = new H2Label("View Order").withExtraBoldFont();
		
		pageContent = new BaseVBox().withNormalSpacing();
		orderHolder = new BaseFlowpane().withLooseSpacing();
		
		user = (User) App.preferences.getValue(App.CURRENT_USER_KEY);
		
		oc = new OrderController();
		orderType = oc.getOrders(user.getUserRole());
		
		backButtonContainer = new BaseHBox().withNormalSpacing();
		backButton = new OutlineButton("<").withBoldFont().withSizeOf(20);
		
		getAccessibleOrders();
		
	}

	@Override
	public void configureElements() {
		pageTitle.getStyleClass().addAll("pb-32");
		
		backButton.setStyle(
				"-fx-padding: 12 20 12 20;"
		);
		
		backButtonContainer.setStyle(
				"-fx-padding: 20 20 20 20;"
		);
		
		if(!(user.getUserRole().equals(UserRole.CASHIER))) {
			backButton.setVisible(false);
			backButton.setManaged(false);			
		}
		
	}

	@Override
	public void initializeEventListeners() {
		backButton.setOnMouseClicked(e -> {
			App.redirectTo(App.sceneBuilder(new temp()));
		});
	}

	@Override
	public void assembleLayout() {
		backButtonContainer.getChildren().add(backButton);
		
		pageIdentifierContainer.getChildren().add(pageTitle);
		
		container.getChildren().addAll(pageIdentifierContainer, pageContent);
		rootElements.getChildren().addAll(backButtonContainer, container);
	}

	@Override
	public void setupScene() {
		setCenter(scrollSupport);
	}
	
	/**
	 * Get orders by validating the accessibility of user's role
	 * @param userRole = the user's role 
	 */
	public void getAccessibleOrders() {
		if (user.getUserRole().equals(UserRole.CHEF)) {
			getOrders(orderType.getPendingOrders());
		}else if (user.getUserRole().equals(UserRole.WAITER)) {
			Label orderStatusLabel = new H5Label("Prepared Orders :");
			pageContent.getChildren().add(orderStatusLabel);
			getOrders(orderType.getPreparedOrders());
			orderStatusLabel = new H5Label("Pending Orders :");
			pageContent.getChildren().add(orderStatusLabel);
			getOrders(orderType.getPendingOrders());
		}else if (user.getUserRole().equals(UserRole.CASHIER)) {
			Label orderStatusLabel = new H5Label("Served Orders :");
			pageContent.getChildren().add(orderStatusLabel);
			getOrders(orderType.getPreparedOrders());
			orderStatusLabel = new H5Label("Prepared Orders :");
			pageContent.getChildren().add(orderStatusLabel);
			getOrders(orderType.getPreparedOrders());
			orderStatusLabel = new H5Label("Pending Orders :");
			pageContent.getChildren().add(orderStatusLabel);
			getOrders(orderType.getPendingOrders());
		}
	}

	/**
	 * Get orders by validating the order's status
	 * @param userStatus = the order's status that the user could access 
	 */	
	public void getOrders(ArrayList<Order> orders) {
		if (!orders.isEmpty()) {
			for (Order order : orders) {
				orderContainer = new BaseVBox().withCustomSpacing(5);
				
				orderContainer.setPrefWidth(200);
				
				orderCardView = new BaseCardView();
				
				VBox buttonContainer = new BaseVBox().withTightSpacing().centerContentBothAxis();
				buttonContainer.setStyle("-fx-padding: 10 0 5 0;");
				
				Label username = new H5Label(order.getOrderUser().getUserName()).setSize(16);
				orderContainer.getChildren().add(username);
				
				for(OrderItem oi : order.getOrderItems()) {
					String orderQuantity = oi.getQuantity().toString();
					String menuitemName = oi.getMenuItem().getMenuItemName();
					
					Label orderItem = new HLabel(orderQuantity + "  X  " + menuitemName).setSize(14);
					
					orderContainer.getChildren().add(orderItem);
				}
				
				Button checkButton = new OutlineButton("Check Order").withSizeOf(14);
				checkButton.setStyle("-fx-padding: 10 20 10 20;");
				
				checkButton.setOnMouseClicked(e -> {
					if (user.getUserRole().equals(UserRole.CASHIER)) {
						App.redirectTo(App.sceneBuilder(new temp()));						
					} else {
						App.redirectTo(App.sceneBuilder(new CheckOrderDetailPage(order)));						
					}
				});
				
				buttonContainer.getChildren().add(checkButton);
				orderContainer.getChildren().add(buttonContainer);
				orderCardView.setContent(orderContainer);
				
				orderHolder.getChildren().addAll(orderCardView.get());
			}
		}
		pageContent.getChildren().add(orderHolder);
		orderHolder = new BaseFlowpane().withLooseSpacing();
	}
	
}

