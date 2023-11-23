package application_starter;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import models.MenuItem;
import repositories.MenuItemRepository;

public class App extends Application {

	public static void main (String [] args) {

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


		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();

	}

}
