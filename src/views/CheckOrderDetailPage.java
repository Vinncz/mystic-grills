package views;

import java.util.ArrayList;
import java.util.Optional;

import application_starter.App;
import controllers.OrderController;
import controllers.OrderItemController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Order;
import models.OrderItem;
import models.User;
import models.User.UserRole;
import models.Order.OrderStatus;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.buttons.EditButton;
import views.components.buttons.OutlineButton;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H2Label;
import views.components.labels.H3Label;
import views.components.labels.H4Label;
import views.components.labels.H5Label;
import views.components.scroll_panes.BaseScrollPane;
import views.components.vboxes.BaseVBox;
import views.components.vboxes.Container;
import views.guidelines.PageDeclarationGuideline_v1;

public class CheckOrderDetailPage extends BorderPane implements PageDeclarationGuideline_v1 {

	private ScrollPane scrollSupport;
	private HBox rootElements;
	private VBox container;
	
	private VBox pageIdentifierContainer;
	private Label pageTitle;
	
	private VBox pageContent, orderContainer, orderItemHolder;
	private HBox orderOwner, buttonContainer;
	private Label userPointer, customerName, orderItemPointer;
	private Button deleteButton, updateButton, prepareButton, serveButton;
	
	private HBox backButtonContainer;
	private Button backButton;
	
	private OrderController oc;
	private Order order;
	private User user;
	
	public CheckOrderDetailPage(Order _order) {
		this.order = _order;
		initializeScene();
	}
	
	@Override
	public void initializeControls() {
		rootElements = new RootElement();
		container = new Container().centerContentHorizontally();
		scrollSupport = new BaseScrollPane(rootElements);
		
		pageIdentifierContainer = new BaseVBox().withTightSpacing().centerContentHorizontally();
			pageTitle = new H2Label("Order Details").withExtraBoldFont();
		
		pageContent = new BaseVBox().withNormalSpacing();
			orderOwner = new BaseHBox().withNormalSpacing();
				userPointer = new H4Label("Nama Customer :");
				customerName = new H4Label(order.getOrderUser().getUserName());
			
			orderContainer = new BaseVBox().withTightSpacing();
				orderItemPointer = new H5Label("Detail Pesanan :");
				orderItemHolder = new BaseVBox().withTightSpacing();
		
		oc = new OrderController();
		user = (User) App.preferences.getValue(App.CURRENT_USER_KEY);
				
		backButtonContainer = new BaseHBox().withNormalSpacing();
			backButton = new OutlineButton("<").withBoldFont().withSizeOf(20);
		
		getOrderDetail();
			
		buttonContainer = new BaseHBox().withLooseSpacing().centerContentHorizontally();
			deleteButton = new DestructiveButton("Delete Order").withBoldFont();
			updateButton = new EditButton("Update Order").withBoldFont();
			prepareButton = new CTAButton("Prepare Order").withBoldFont();
			serveButton = new CTAButton("Serve Order").withBoldFont();
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
		
		pageContent.setStyle(
				"-fx-border-color: -primary-gray;"
				+ "-fx-padding: 15 15 15 15;"
				+ "-fx-border-width: 1px;"
				+ "-fx-border-style: none none solid none;"
				+ "-fx-border-radius: 10;"
		);
		
		orderContainer.setStyle(
				"-fx-border-color: -primary-gray;"
				+ "-fx-padding: 15 0 0 0;"
				+ "-fx-border-width: 1px;"
				+ "-fx-border-style: solid none none none;"
		);
		
		buttonContainer.setStyle(
				"-fx-padding:40 0 0 0;"
		);
		
		orderItemHolder.setStyle("-fx-padding: 0 0 0 20;");
		
		deleteButton.setStyle("-fx-padding: 12 35 12 35;");
		updateButton.setStyle("-fx-padding: 12 35 12 35;");
		prepareButton.setStyle("-fx-padding: 12 35 12 35;");
		serveButton.setStyle("-fx-padding: 12 35 12 35;");
		
		
		serveButton.setVisible(false);
		serveButton.setManaged(false);
		prepareButton.setVisible(false);
		prepareButton.setManaged(false);
		
		if (order.getOrderStatus().equals(OrderStatus.PENDING) 
				&& user.getUserRole().equals(UserRole.CHEF)) {
			prepareButton.setVisible(true);
			prepareButton.setManaged(true);
		}else if (order.getOrderStatus().equals(OrderStatus.PREPARED) 
				&& user.getUserRole().equals(UserRole.WAITER)) {			
			serveButton.setVisible(true);
			serveButton.setManaged(true);			
		}
		
	}

	@Override
	public void initializeEventListeners() {
		backButton.setOnMouseClicked(e -> {
			App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
		});
		
		deleteButton.setOnMouseClicked(e -> {
			if (oc.validateDeleteOrder(order.getOrderId())) {
				App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
			}
		});
		
		updateButton.setOnMouseClicked(e -> {
			App.redirectTo(App.sceneBuilder(new UpdateOrderPage(order)));
		});
		
		prepareButton.setOnMouseClicked(e -> {
			if(oc.prepareOrder(order)) {
				App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
			}
		});
		
		serveButton.setOnMouseClicked(e -> {
			if(oc.serveOrder(order)) {
				App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
			}
		});
	}

	@Override
	public void assembleLayout() {
		backButtonContainer.getChildren().add(backButton);
		
		pageIdentifierContainer.getChildren().add(pageTitle);
		
		orderOwner.getChildren().addAll(userPointer, customerName);
		
		orderContainer.getChildren().addAll(orderItemPointer, orderItemHolder);
		
		pageContent.getChildren().addAll(orderOwner, orderContainer);
		
		buttonContainer.getChildren().addAll(
				deleteButton, 
				updateButton, 
				prepareButton, 
				serveButton
		);
		
		container.getChildren().addAll(
				pageIdentifierContainer, 
				pageContent, 
				buttonContainer
		);
		rootElements.getChildren().addAll(backButtonContainer, container);
	}

	@Override
	public void setupScene() {
		setCenter(scrollSupport);
	}
	
	public void getOrderDetail() {
		
		for(OrderItem oi : order.getOrderItems()) {
			String orderQuantity = oi.getQuantity().toString();
			String menuitemName = oi.getMenuItem().getMenuItemName();
			
			Label orderItem = new H4Label(orderQuantity + "  X  " + menuitemName).withBoldFont();
			
			orderItemHolder.getChildren().add(orderItem);
		}
	}

}