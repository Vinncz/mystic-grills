package application_starter;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.MenuItem;
import models.Order;
import models.OrderItem;
import repositories.MenuItemRepository;
import repositories.OrderRepository;
import values.SYSTEM_PROPERTIES;
import values.SharedPreference;
import views.LoginPage;

public class App extends Application {

	private static Stage primaryStage;
	public  static SharedPreference preferences = new SharedPreference();

	public static Integer stagePadding = 60;

	public static void main (String [] args) {

		// debugMenuItemRepository();
		// debugOrderRepository();

		launch(args);

	}

	public static void debugOrderRepository () {
		OrderRepository orepo = new OrderRepository();
		ArrayList<Order> orders = orepo.getAll();

		// System.out.println("\n\nDELETE ALL BELOW ___");
		// for (Order o : orders) {
		// 	orepo.delete(o.getOrderId());
		// }

		// Optional<Order> opto = orepo.getById(1);
		// if (opto.isPresent()) {
		System.out.println("");
		for (Order o : orders) {
			boolean flag = false;
			// Order o = opto.get();
			System.out.println("OrderID: " + o.getOrderId());
			System.out.println("    OrderDate: " + o.getOrderDate());
			System.out.println("    OrderTotal: " + o.getOrderTotal());
			System.out.println("    OrderStatus: " + o.getOrderStatus());

			System.out.println("    Order User:");
			System.out.println("        Order UserID: " + o.getOrderUser().getUserId());
			System.out.println("        Order UserName: " + o.getOrderUser().getUserName());
			System.out.println("        Order UserEmail: " + o.getOrderUser().getUserEmail());
			System.out.println("        Order UserPassword: " + o.getOrderUser().getUserPassword());
			System.out.println("        Order UserRole: " + o.getOrderUser().getUserRole());

			System.out.println("    Order Items:");
			for (OrderItem oi : o.getOrderItems()) {
				flag = true;
				System.out.println("        OrderItem ID: " + oi.getOrderItemId());
				System.out.println("            OrderItem Quantity: " + oi.getQuantity());
				System.out.println("            OrderItem's OrderID: " + oi.getOrderId());

				System.out.println("            OrderItem's Menu Item:");
				System.out.println("                 OrderItem's MenuItem Id: " + oi.getMenuItem().getMenuItemId());
				System.out.println("                 OrderItem's MenuItem Name: " + oi.getMenuItem().getMenuItemName());
				System.out.println("                 OrderItem's MenuItem Desc: " + oi.getMenuItem().getMenuItemDescription());
				System.out.println("                 OrderItem's MenuItem Price: " + oi.getMenuItem().getMenuItemPrice());
			}

			if (flag == false) {
				System.out.println("        none.");
			}
		}

	}

	public static void debugMenuItemRepository() {
		MenuItemRepository mirepo = new MenuItemRepository();
		ArrayList<MenuItem> menuItems = mirepo.getAll();

		System.out.println("\n\nDELETE ALL BELOW ___");
		for (MenuItem mi : menuItems) {
			mirepo.delete(mi.getMenuItemId());
		}

		System.out.println("\n\nFETCH ALL BELOW ___");
		menuItems = mirepo.getAll();
		for (MenuItem m : menuItems) {
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		System.out.println("\n\nINSERT ONE BELOW ___");
		MenuItem mi = new MenuItem();
		mi.setMenuItemName("Indomie Goreng Jumbo");
		mi.setMenuItemDescription("Lebih nikmat disajikan dengan telur");
		mi.setMenuItemPrice(3900);
		mi = mirepo.post(mi);

		System.out.println("\n\nFETCH ALL BELOW ___");
		menuItems = mirepo.getAll();
		for (MenuItem m : menuItems) {
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		System.out.println("\n\nUPDATE ONE BELOW ___");
		mi.setMenuItemName("Mie Suksess Isi 2");
		mi.setMenuItemDescription("Isinya boleh 2, rasanya gaenak!");
		mi.setMenuItemPrice(3500);
		mirepo.put(mi);

		System.out.println("\n\nFETCH ALL BELOW ___");
		menuItems = mirepo.getAll();
		for (MenuItem m : menuItems) {
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		System.out.println("\n\nDELETE ONE BELOW ___");
		mirepo.delete(mi.getMenuItemId());

		System.out.println("\n\nFETCH ALL BELOW ___");
		menuItems = mirepo.getAll();
		for (MenuItem m : menuItems) {
			System.out.println("MASUK SINI");
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}
	}

	@Override
	public void start(Stage _primaryStage) throws Exception {
		primaryStage = _primaryStage;

		final Scene defaultStartupScene = attachStylesheet( sceneBuilder( new LoginPage() ) );
		primaryStage.setScene(defaultStartupScene);

		primaryStage.setMinHeight(Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_HEIGHT.value) + (stagePadding * 2) );
		primaryStage.setMinWidth (Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_MIN_WIDTH.value ) + (stagePadding * 2) );

		primaryStage.setResizable(true);
		primaryStage.setTitle("");

		primaryStage.show();

	}

	/**
	 * Attaches the global css file to the passed scene argument.
	 *
	 * @param _target
	 * @return The passed scene argument, with its stylesheet attribute linked to the global css file.
	 */
	private static Scene attachStylesheet (Scene _target) {
		_target.getStylesheets().add(
			App.class.getResource("/views/styles/global.css").toExternalForm()
		);

		return _target;
	}

	/**
     * Replaces whichever Scene the PrimaryStage is showing you, with the passed argument.
	 * <br></br>
	 * <b> DO NOTE </b> that the passed scene <b> MUST NOT </b> be the same instance as the scene which is shown.
	 *
     * @param _targetScene • The scene who will replace the scene that is currently shown.
     */
    public static void redirectTo (Scene _targetScene) {
		_targetScene = attachStylesheet(_targetScene);

		primaryStage.setScene(_targetScene);
	}

	/**
	 * Simplifies the creation of a new scene, using the system properties' application scene target width/height.
	 *
	 * @param _page
	 * @return The created scene object
	 */
	public static Scene sceneBuilder (Pane _page) {
		return new Scene (
			_page,
			Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_WIDTH.value),
			Integer.parseInt(SYSTEM_PROPERTIES.APPLICATION_TARGET_HEIGHT.value)
		);
	}

}
