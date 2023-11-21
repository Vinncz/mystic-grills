package application_starter;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import models.MenuItem;
import repositories.MenuItemRepository;

public class App extends Application {

	public static void main (String [] args) {
		MenuItemRepository mirepo = new MenuItemRepository();
		Optional<MenuItem> mi = mirepo.getById(1);

		if (mi.isPresent()) {
			MenuItem m = mi.get();

			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		System.out.println("\n\n");

		ArrayList<MenuItem> mis = mirepo.getAll();
		for (MenuItem m : mis) {
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		System.out.println("\n\n");

		MenuItem minew = new MenuItem();
		minew.setMenuItemName("Indomie Kari Ayam");
		minew.setMenuItemDescription("Lebih nikmat disajikan dengan telur");
		minew.setMenuItemPrice(12_000);

		minew = mirepo.post(minew);
		System.out.println("id: " + minew.getMenuItemId());
		System.out.println("name: " + minew.getMenuItemName());
		System.out.println("desc: " + minew.getMenuItemDescription());
		System.out.println("price: " + minew.getMenuItemPrice());

		System.out.println("\n\n");
		
		mis = mirepo.getAll();
		for (MenuItem m : mis) {
			System.out.println("id: " + m.getMenuItemId());
			System.out.println("name: " + m.getMenuItemName());
			System.out.println("desc: " + m.getMenuItemDescription());
			System.out.println("price: " + m.getMenuItemPrice());
		}

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();

	}

}
