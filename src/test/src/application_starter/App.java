package test.src.application_starter;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import test.src.models.MenuItem;
import test.src.models.Order;
import test.src.models.OrderItem;
import test.src.models.Receipt;
import test.src.models.User;
import test.src.models.Receipt.ReceiptPaymentType;
import test.src.models.User.UserRole;

public class App extends Application {

	private static String[] MenuItemNames = {
        "",
        "Indomie Goreng Jumbo",
        "Coca Cola Zero Sugar",
        "Mixue Supreme Milk Tea",
        "Aqua Botol 600ml",
        "Aice Strawberry Crispy 90ml"
    };

    private static String[] MenuItemDescription= {
        "",
        "Nikmat disajikan dengan telur",
        "Nikmat disajikan dingin",
        "Disajikan segera",
        "Nikmat disajikan saat haus",
        "Nikmat disajikan sebelum meleleh"
    };

    final static int LIMIT = 5;
    public static void main (String [] args) {
        /*
         * RUN `php artisan migrate:fresh` FIRST ON YOUR DB
         * BEFORE COMMENCING TEST
         */

        // Boolean user_passed =      testUsers        (true, true, true, false);
        // Boolean menuItem_passed =  testMenuItem     (true, true, true, false);
        // Boolean order_passed =     testOrders       (true, true, true, false);
        // Boolean orderItem_passed = testOrderItem    (true, true, true, false);
        // Boolean receipt_passed =   testReceipts     (true, true, true, false);

        // String verdict = "All test succeed";
        // if ( !(user_passed && order_passed && receipt_passed && orderItem_passed && menuItem_passed) ) {
        //     verdict = "Some test failed";
        // }

        // Log(verdict);

        User u = User.createUser(UserRole.CUSTOMER, "Kevin Gunawan", "kevin.gunawan010@gmail.com", "password");
        LogUser(u);

        MenuItem mi = MenuItem.createMenuItem("Indomie Goreng Special Jumbo", "Mantap pokoknya!", 12_000);
        LogMenuItem(mi);

        Order o = Order.createOrder(u, new ArrayList<OrderItem>(), "2023-10-08 22:39:00");
        LogOrder(o);

        OrderItem oi = OrderItem.createOrderItem(o.getOrderId(), mi, 1);
        ArrayList<OrderItem> ois = new ArrayList<>();
        ois.add(oi);
        Order.updateOrder(o.getOrderId(), ois, o.getOrderStatus().toString());

        Receipt r = Receipt.createReceipt(o, ReceiptPaymentType.CASH, 20_000.0, "2023-10-08 22:43:00");
        LogReceipt(r);
    }

    private static Boolean testUsers (Boolean get, Boolean post, Boolean patch, Boolean delete) {
        Boolean success = true;

        try {
            if (get) {
                Log("Fetching All Users");
                ArrayList<User> usersFromDb = User.getAllUsers();
                for (User user : usersFromDb) {
                    LogUser(user);
                }

            }

            if (post) {
                Log("Creating 5 Users");
                for (int i = 1; i <= LIMIT; i++) {
                    User u = User.createUser(UserRole.CUSTOMER, "TestUser" + i, "testuser"+i+"@gmail.com", "password");
                    LogUser(u);
                }
            }


            if (patch) {
                Log("Updating 5 Users");
                for (int i = 1; i <= LIMIT; i++) {
                    User.updateUser(i, UserRole.CUSTOMER, "TestUser" +i, "testuser"+i+"@gmail.com", "password");
                }
            }

            if (delete) {
                Log("Deleting 5 Users");
                for (int i = 1; i <= LIMIT; i++) {
                    User.deleteUser(i);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            success = false;

        }

        return success;
    }

    private static Boolean testOrders (Boolean get, Boolean post, Boolean patch, Boolean delete) {
        Boolean success = true;

        try {
            if (get) {
                Log("Fetching All Orders");
                ArrayList<Order> ordersFromDb = Order.getAllOrders();
                for (Order order : ordersFromDb) {
                    LogOrder(order);
                }

            }

            if (post) {
                Log("Creating 5 Orders");
                for(int i = 1; i <= LIMIT; i++) {
                    Order o = Order.createOrder(User.getUserById(i), OrderItem.getAllOrderItemsByOrderId(i), "2023-10-5 23:40");
                    LogOrder(o);
                }
            }


            if (patch) {
                Log("Updating 5 Orders");
                for (int i = 1; i <= LIMIT; i++) {
                    Order.updateOrder(i, OrderItem.getAllOrderItemsByOrderId(i), Order.OrderStatus.PAID.toString());
                }
            }

            if (delete) {
                Log("Deleting 5 Orders");
                for (int i = 1; i <= LIMIT; i++) {
                    Order.deleteOrder(i);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            success = false;

        }

        return success;
    }

    private static Boolean testReceipts (Boolean get, Boolean post, Boolean patch, Boolean delete) {
        Boolean success = true;

        try {
            if (get) {
                Log("Fetching All Receipts");
                ArrayList<Receipt> receiptsFromDb = Receipt.getAllReceipts();
                for (Receipt receipt : receiptsFromDb) {
                    LogReceipt(receipt);
                }
            }

            if (post) {
                Log("Creating 5 Receipts");
                for (int i = 1; i <= LIMIT; i++) {
                    Order o = Order.getOrderByOrderId(i);

                    Receipt r = Receipt.createReceipt(o, ReceiptPaymentType.CASH, 20.00, "2023-10-5 23:44:00");
                    LogReceipt(r);
                }
            }

            if (patch) {
                Log("Updating 5 Receipts");
                for (int i = 1; i <= LIMIT; i++) {
                    Receipt.updateReceipt(i, ReceiptPaymentType.CASH, 20.00, "2023-10-5 23:44");
                }
            }

            if (delete) {
                Log("Deleting 5 Receipts");
                for (int i = 1; i <= LIMIT; i++) {
                    Receipt.deleteReceipt(i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            success = false;

        }

        return success;
    }

    private static Boolean testMenuItem (Boolean get, Boolean post, Boolean patch, Boolean delete) {
        Boolean success = true;

        try {
            if (get) {
                Log("Fetching All MenuItems");
                ArrayList<MenuItem> menuItemsFromDb = MenuItem.getAllMenuItems();
                for (MenuItem menuItem : menuItemsFromDb) {
                    LogMenuItem(menuItem);
                }

            }

            if (post) {
                Log("Creating 5 MenuItems");
                for (int i = 1; i <= LIMIT; i++) {
                    MenuItem mi = MenuItem.createMenuItem(MenuItemNames[i], MenuItemDescription[i], 20+i);
                    LogMenuItem(mi);
                }
            }

            if (patch) {
                Log("Updating 5 MenuItems");
                for (int i = 1; i <= LIMIT; i++) {
                    MenuItem.updateMenuItem(i, MenuItemNames[i], MenuItemDescription[i], 20+i);
                }
            }

            if (delete) {
                Log("Deleting 5 MenuItems");
                for (int i = 1; i <= LIMIT; i++) {
                    MenuItem.deleteMenuItem(i);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            success = false;

        }

        return success;
    }

    private static Boolean testOrderItem (Boolean get, Boolean post, Boolean patch, Boolean delete) {
        Boolean success = true;

        try {
            if (get) {
                Log("Fetching All OrderItem");
                ArrayList<OrderItem> orderItemsFromDb = OrderItem.getAllOrderItemsByOrderId(1);
                for (OrderItem orderItem : orderItemsFromDb) {
                    LogOrderItem(orderItem);
                }

            }

            if (post) {
                Log("Creating 5 OrderItem");
                for (int i = 1; i <= LIMIT; i++) {
                    OrderItem oi = OrderItem.createOrderItem(i, MenuItem.getMenuItemById(i), i);
                    LogOrderItem(oi);
                }
            }

            if (patch) {
                Log("Updating 5 OrderItem");
                for (int i = 1; i <= LIMIT; i++) {
                    OrderItem.updateOrderItem(i, MenuItem.getMenuItemById(i), i);
                }
            }

            if (delete) {
                Log("Deleting 5 OrderItem");
                for (int i = 1; i <= LIMIT; i++) {
                    OrderItem.deleteOrderItem(i, i);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            success = false;

        }

        return success;
    }


    /*
     * LOGGING
     */
    private static void LogUser (User user) {
        Log("USER ID:\t\t" + user.getUserId().toString());
        Log("USER Name:\t\t" + user.getUserName());
        Log("USER Email:\t\t" + user.getUserEmail());
        Log("USER Password:\t\t" + user.getUserPassword());
        Log("USER Role:\t\t" + user.getUserRole().toString());
        Log("");
    }

    private static void LogOrderItem (OrderItem oi) {
        Log("ORDER_ITEM ID:\t\t" + oi.getOrderId().toString());
        Log("ORDER_ITEM Quantity:\t" + oi.getQuantity().toString());
        LogMenuItem(oi.getMenuItem());
        Log("");
    }

    private static void LogMenuItem (MenuItem mi) {
        Log("MENU_ITEM ID:\t\t" + mi.getMenuItemId().toString());
        Log("MENU_ITEM Name:\t\t" + mi.getMenuItemName());
        Log("MENU_ITEM Description:\t" + mi.getMenuItemDescription());
        Log("MENU_ITEM Price:\t" + mi.getMenuItemPrice().toString());
        Log("");
    }

    private static void LogOrder (Order o) {
        Log("ORDER ID:\t" + o.getOrderId().toString());
        Log("ORDER Date:\t" + o.getOrderDate());
        Log("ORDER Status:\t" + o.getOrderStatus().toString());
        Log("ORDER Total:\t" + o.getOrderTotal().toString());
        LogUser(o.getOrderUser());

        ArrayList<OrderItem> orderItems = o.getOrderItems();
        for (OrderItem oi : orderItems) {
            LogOrderItem(oi);
        }
        Log("");
    }

    private static void LogReceipt (Receipt r) {
        Log("RECEIPT ID:\t\t" + r.getReceiptId().toString());
        Log("RECEIPT Payment Type:\t" + r.getReceiptPaymentType().toString());
        Log("RECEIPT Payment Date:\t" + r.getReceiptPaymentDate());
        Log("RECEIPT Paid Amount:\t" + r.getReceiptAmountPaid().toString());
        LogOrder(r.getReceiptOrder());
        Log("");
    }

    private static void Log (String _s) {
        System.out.println(_s);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();

	}

}
