package views;

import java.util.ArrayList;
import java.util.stream.Collectors;

import application_starter.App;
import controllers.MenuItemController;
import controllers.OrderController;
import controllers.OrderItemController;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.MenuItem;
import models.Order;
import models.OrderItem;
import models.User;
import views.components.buttons.CTAButton;
import views.components.buttons.DestructiveButton;
import views.components.buttons.EditButton;
import views.components.buttons.OutlineButton;
import views.components.combo_boxes.BaseComboBox;
import views.components.combo_boxes.BaseComboBoxBuilder;
import views.components.combo_boxes.ComboBoxData;
import views.components.hboxes.BaseHBox;
import views.components.hboxes.RootElement;
import views.components.labels.H1Label;
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

public class UpdateOrderPage extends BorderPane implements PageDeclarationGuideline_v1 {

	private ScrollPane scrollSupport;
	private HBox rootElements;
	private VBox container;
	
	private VBox pageIdentifierContainer;
	private Label pageTitle;
	
	private VBox pageContent, orderContainer, orderItemHolder;
	private HBox orderOwner, buttonContainer;
	private Label userPointer, customerName, orderItemPointer;
	private Button saveButton;
	
	private HBox addOrderItemContainer, addMenuItemContainer, addOrderItemButtonContainer;
	private Label newOrderItemLabel;
	private Button addOrderItemButton;
	
	private HBox backButtonContainer;
	private Button backButton;
	
	private OrderController oc;
	private OrderItemController oic;
	private MenuItemController mic;
	private Order order;
	private ArrayList<OrderItem> orderItems;
	private ArrayList<MenuItem> menuItems;
	
	private BaseNumberfieldBuilder baseNumberfieldBuilder;
    private BaseNumberfield baseNumberfieldObj;
    private ArrayList<BaseNumberfield> orderItemNumberFields;
	
    private BaseComboBoxBuilder miComboBoxBuilder;
    private ArrayList<ComboBoxData> miComboBoxData;
    private ComboBox<ComboBoxData> menuItemComboBox;
    
	public UpdateOrderPage(Order _order) {
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
		
		backButtonContainer = new BaseHBox().withNormalSpacing();
			backButton = new OutlineButton("<").withBoldFont().withSizeOf(20);
		
		addOrderItemContainer = new BaseHBox().withCustomSpacing(100).centerContentHorizontally();
			addMenuItemContainer = new BaseHBox().withLooseSpacing().centerContentVertically();
				newOrderItemLabel = new H4Label("Add New Order Item : ");
			addOrderItemButtonContainer = new BaseHBox().withTightSpacing().centerContentVertically();
				addOrderItemButton = new CTAButton("Add");
			
		oc = new OrderController();
		oic= new OrderItemController();
		mic = new MenuItemController();
		orderItems = order.getOrderItems();
		orderItemNumberFields = new ArrayList<>();
		menuItems = mic.getAll();
		
		getOrderDetail();
		
		setComboBox();
		
		buttonContainer = new BaseHBox().withLooseSpacing().centerContentHorizontally();
			saveButton = new CTAButton("Save Changes").withBoldFont();
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
		
		saveButton.setStyle("-fx-padding: 12 35 12 35;");
	}

	@Override
	public void initializeEventListeners() {
		backButton.setOnMouseClicked(e -> {
			App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
		});
		
		addOrderItemButton.setOnMouseClicked(e -> {
			MenuItem newMenuItem = (MenuItem) menuItemComboBox
					.getSelectionModel()
					.getSelectedItem()
					.getSupportingData();
			
			OrderItem oi = oic.newOrderItem(order.getOrderId(), newMenuItem);
			
			String menuitemName = oi.getMenuItem().getMenuItemName();
				
			HBox orderItemDetail = new BaseHBox()
					.withLooseSpacing()
					.centerContentVertically();
			Label orderItem = new H4Label(menuitemName).withBoldFont();			
			orderItem.setPrefWidth(400);
			baseNumberfieldBuilder = new BaseNumberfieldBuilder()
					.withMaximumValueOf(10)
					.withMinimumValueOf(0)
					.withInitialValueOf(oi.getQuantity());
			baseNumberfieldObj = baseNumberfieldBuilder.build();
				
			orderItemDetail.getChildren().addAll(orderItem, baseNumberfieldObj);
			orderItemHolder.getChildren().addAll(orderItemDetail);
			OrderItem newOI = oic.addOrderItems(oi);
			orderItems.add(newOI);
			orderItemNumberFields.add(baseNumberfieldObj);
			
			menuItems = (ArrayList<MenuItem>) menuItems.stream()
					.filter(mi -> !(mi.getMenuItemId() == newOI.getMenuItem().getMenuItemId()))
					.collect(Collectors.toList()); 
			
			setComboBox();
			assembleLayout();
		});
		
		saveButton.setOnMouseClicked(e -> {
			for (BaseNumberfield numberfield : orderItemNumberFields) {
				Integer quantity = Integer.parseInt(numberfield.getInputField().getText());
				
				OrderItem oi = orderItems.get(orderItemNumberFields.indexOf(numberfield));
				oi.setQuantity(quantity);
				
				if (oi.getQuantity() == 0) {
					if (oic.validateDeleteOrderItem(oi.getOrderItemId())) {
						System.out.println("Success Deleting Order Item");
					}
				} else {
					oic.updateChanges(oi);
				}
			}
			
			App.redirectTo(App.sceneBuilder(new ViewOrderPage()));
			
		});
	}

	@Override
	public void assembleLayout() {
		backButtonContainer.getChildren().setAll(backButton);
		
		pageIdentifierContainer.getChildren().setAll(pageTitle);
		
		orderOwner.getChildren().setAll(userPointer, customerName);
		
		orderContainer.getChildren().setAll(orderItemPointer, orderItemHolder);
		
		pageContent.getChildren().setAll(orderOwner, orderContainer);
		
		addMenuItemContainer.getChildren().setAll(
				newOrderItemLabel, menuItemComboBox
		);
		
		addOrderItemButtonContainer.getChildren().setAll(addOrderItemButton);
		
		addOrderItemContainer.getChildren().setAll(
				addMenuItemContainer, addOrderItemButtonContainer
		);
		
		buttonContainer.getChildren().setAll(saveButton);
		
		container.getChildren().setAll(
				pageIdentifierContainer,
				pageContent,
				addOrderItemContainer,
				buttonContainer
		);
		
		rootElements.getChildren().setAll(backButtonContainer, container);
	}

	@Override
	public void setupScene() {
		setCenter(scrollSupport);
	}
	
	public void getOrderDetail() {
		
		for(OrderItem oi : orderItems) {
			String menuitemName = oi.getMenuItem().getMenuItemName();
			
			HBox orderItemDetail = new BaseHBox()
					.withLooseSpacing()
					.centerContentVertically();
			Label orderItem = new H4Label(menuitemName).withBoldFont();
			orderItem.setPrefWidth(400);
			baseNumberfieldBuilder = new BaseNumberfieldBuilder()
					.withMaximumValueOf(10)
					.withMinimumValueOf(0)
					.withInitialValueOf(oi.getQuantity());
			baseNumberfieldObj = baseNumberfieldBuilder.build();
			
			orderItemDetail.getChildren().addAll(orderItem, baseNumberfieldObj);

			orderItemHolder.getChildren().addAll(orderItemDetail);
			
			orderItemNumberFields.add(baseNumberfieldObj);
			
			menuItems = (ArrayList<MenuItem>) menuItems.stream()
					.filter(mi -> !(mi.getMenuItemId() == oi.getMenuItem().getMenuItemId()))
					.collect(Collectors.toList()); 
		}
	}
	
	public void setComboBox() {
		miComboBoxData = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			ComboBoxData cbData = new ComboBoxData(menuItem.getMenuItemName(), menuItem);
			miComboBoxData.add(cbData);	
		}
		
		miComboBoxBuilder = new BaseComboBoxBuilder()
				.withDataOf(miComboBoxData)
				.withInitialValueOf(miComboBoxData.get(0));
		menuItemComboBox = miComboBoxBuilder.build();
		System.out.println(miComboBoxData.size());
	}

}
