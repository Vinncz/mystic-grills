package application_starter;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import models.MenuItem;
import models.Order;
import models.OrderItem;
import repositories.MenuItemRepository;
import repositories.OrderRepository;

public class App extends Application {

	public static void main (String [] args) {

		// debugMenuItemRepository();
		debugOrderRepository();

		launch(args);

	}

	private static void debugOrderRepository () {
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

	private static void debugMenuItemRepository() {
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
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();

	}

}
