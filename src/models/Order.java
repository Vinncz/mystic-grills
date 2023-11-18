package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database_access.ConnectionMaster;
import models.helpers.ConnectsWithDatabase;

public class Order extends ConnectsWithDatabase {

    public enum OrderStatus {
        PAID,
        PENDING,
        PREPARED,
        SERVED,
    }

    private static Connection db = ConnectionMaster.getConnection();
    private static final String TABLE_NAME = "orders";

    private Integer orderId;
    private User orderUser;
    private ArrayList<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private String orderDate;
    private Double orderTotal;





    /*
     * ==========================================================================
     *
     * (!)
     * DATABASE METHODS
     *
     * The following should be static.
     *
     * ==========================================================================
     */

    /*
     * GET
     */
    public static ArrayList<Order> getOrdersByCustomerId(int _customerId) {

        /* TEMPLATE QUERY SHOULD BE FINAL */
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " o WHERE o.user_id = ?";

        /* PREPARE CONTAINER FOR THE QUERIED DATA */
        ArrayList<Order> target = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);

            /*
            * (!)
            * Set the prepared statement's arguments here.
            *
             * Definition of an 'argument' are those '?' (Question marks) that are inside the template query.
             * If there are only one '?', then only a single line of setMethods() are needed.
             * Should there be more than one, you can simply do the following:
             *
             * EXAMPLE TEMPLATE QUERY:
             * "SELECT * FROM orders o WHERE o.user_id = ? AND o.date > ?"
             *
             * WHAT YOU SHOULD DO:
             * ps.setInt(1, _customerId)  -->  '1' REPRESENTS THE FIRST '?'
             * ps.setDate(2, _orderDate)  -->  '2' REPRESENTS THE SECOND '?'
             */
            ps.setInt(1, _customerId);

            ResultSet res = ps.executeQuery();

            /* As long as MySQL returns a non-empty query results, do the following */
            while ( res.next() ) {

                /* Make an empty object first */
                Order o = new Order();

                /* Outsource related objects from its parent class' methods */
                User u = User.getUserById(res.getInt("user_id"));
                OrderStatus status = OrderStatus.valueOf(res.getString("status"));

                o.setOrderId     (res.getInt("id"));
                o.setOrderDate   (res.getTimestamp("date").toString());
                o.setOrderTotal  (res.getDouble("total"));
                o.setOrderUser   (u);
                o.setOrderStatus (status);
                o.setOrderItems  (OrderItem.getAllOrderItemsByOrderId(o.orderId));

                target.add(o);
            }

            /* Close the uneeded PreparedStatement and ResultSet */
            ps.close();
            res.close();

        } catch (SQLException e) {
            /* When query operations encountered an exception, it will be handled here */
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            /* When enumerals attribute encountered an exception, it will be handled here */
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            /* When there are an exception we didn't foresee, it will be handled here */
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return target;
    }

    public static ArrayList<Order> getAllOrders() {

        /*
         * (!)
         * A DEFINITIVE GUIDE THAT DICTATES
         * HOW SHOULD YOU WRITE THIS METHOD
         * ARE LOCATED INSIDE getOrdersByCustomerId().
         */

        final String QUERY = "SELECT * FROM " + TABLE_NAME;

        ArrayList<Order> target = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);
            ResultSet res = ps.executeQuery();

            while ( res.next() ) {
                Order o = new Order();

                User u = User.getUserById(res.getInt("user_id"));
                OrderStatus status = OrderStatus.valueOf(res.getString("status"));

                o.setOrderId     (res.getInt("id"));
                o.setOrderDate   (res.getTimestamp("date").toString());
                o.setOrderTotal  (res.getDouble("total"));
                o.setOrderUser   (u);
                o.setOrderStatus (status);
                o.setOrderItems  (OrderItem.getAllOrderItemsByOrderId(o.orderId));

                target.add(o);
            }

            ps.close();
            res.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return target;
    }

    public static Order getOrderByOrderId(int _orderId) {

        /*
         * (!)
         * A DEFINITIVE GUIDE THAT DICTATES
         * HOW SHOULD YOU WRITE THIS METHOD
         * ARE LOCATED INSIDE getOrdersByCustomerId().
         */

        final String QUERY = "SELECT * FROM " + TABLE_NAME + " o WHERE o.id = ?";

        Order target = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);
            ps.setInt(1, _orderId);
            System.out.println(ps.toString());

            ResultSet res = ps.executeQuery();

            while ( res.next() ) {
                User u = User.getUserById(res.getInt("user_id"));
                OrderStatus status = OrderStatus.valueOf(res.getString("status"));

                target = new Order();
                target.setOrderId     (res.getInt("id"));
                target.setOrderDate   (res.getTimestamp("date").toString());
                target.setOrderTotal  (res.getDouble("total"));
                target.setOrderUser   (u);
                target.setOrderStatus (status);
                target.setOrderItems  (OrderItem.getAllOrderItemsByOrderId(target.orderId));
            }

            ps.close();
            res.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return target;
    }

    /*
     * POST
     */
    public static Order createOrder(User _orderUser, ArrayList<OrderItem> _orderItems, String _orderDate)  {

        /* Criteria 3 - ... all initial order have a status “Pending”.  */
        final String INITIAL_STATUS = OrderStatus.PENDING.toString();

        /* TEMPLATE QUERY SHOULD BE FINAL */
        final String QUERY = "INSERT INTO " + TABLE_NAME + " (user_id, status, date, total) VALUES (?, ?, ?, ?)";

        /* Make an empty object */
        Order o = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);

            /* Fill the arguments with the provided parameters */
            ps.setInt(1, _orderUser.getUserId());
            ps.setString(2, INITIAL_STATUS);
            ps.setString(3, _orderDate);
            ps.setDouble(4, 0.0);

            /* For INSERTION/UPDATION/DELETION, we use executeUpdate() instead of executeQuery */
            int rowsAffected = ps.executeUpdate();

            /* ALWAYS summarize your query execution INSIDE insert/update/delete methods */
            SummarizeDatabaseExecution("createOrder", ps.toString(), rowsAffected);

            /* If database cannot commit to a transaction, stop any object creation */
            if ( !Commit(db, ps.toString()) )
                throw new Exception();



            /* If a new order is successfully made, then you can safely insert its order_items */
            ResultSet generatedKeys  = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                int generatedId = generatedKeys.getInt(1);
                for (OrderItem oi : _orderItems) {
                    OrderItem.createOrderItem(generatedId, oi.getMenuItem(), oi.getQuantity());
                }

                o = new Order();

                o.setOrderId(generatedId);
                o.orderUser = _orderUser;
                o.orderItems = _orderItems;
                o.orderDate = _orderDate;
                o.orderStatus = Order.OrderStatus.PENDING;
                o.orderTotal = 0.0;

            }


            /* Close the uneeded PreparedStatement */
            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            /*
             * There are differences between GET methods and POST/PATCH/DELETE methods.
             *
             * The most important one is that:
             * If an IllegalArgumentException was thrown, then it indicates that the query is trying to insert/modify/delete
             * more than one row of data inside the database.
             */
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return o;
    }

    /*
     * PATCH
     */
    public static Boolean updateOrder(int _orderId, ArrayList<OrderItem> _orderItems, String _orderStatus) {

        /*
         * (!)
         * A DEFINITIVE GUIDE THAT DICTATES
         * HOW SHOULD YOU WRITE THIS METHOD
         * ARE LOCATED INSIDE createOrder().
         */

        final String QUERY = "UPDATE " + TABLE_NAME + " o SET o.status = ? WHERE o.id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(QUERY);

            ps.setString(1, _orderStatus);
            ps.setInt(2, _orderId);



            /* Update every order_items that are associated with _orderId */
            for (OrderItem oi : _orderItems) {
                OrderItem.updateOrderItem(oi.getOrderId(), oi.getMenuItem(), oi.getQuantity());
            }



            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("updateOrder", ps.toString(), rowsAffected);

            if ( !Commit(db, ps.toString()) )
                throw new Exception();

            successful = true;
            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return successful;
    }

    /*
     * DELETE
     */
    public static Boolean deleteOrder(int _orderId) {

        /*
         * (!)
         * A DEFINITIVE GUIDE THAT DICTATES
         * HOW SHOULD YOU WRITE THIS METHOD
         * ARE LOCATED INSIDE createOrder().
         */

        final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(QUERY);

            ps.setInt(1, _orderId);



            /* Delete every order items ascosiated with this orderId */
            ArrayList<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(_orderId);
            for (OrderItem oi : orderItems) {
                OrderItem.deleteOrderItem(oi.getOrderId(), oi.getMenuItem().getMenuItemId());
            }



            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("deleteOrder", ps.toString(), rowsAffected);

            if ( !Commit(db, ps.toString()) )
                throw new Exception();

            successful = true;

            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return successful;

    }





    /*
     * ==========================================================================
     *
     * (!)
     * GETTERS & SETTERS
     *
     * The following should NOT be static.
     *
     * ==========================================================================
     */
    public String getOrderDate() {
        return orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public User getOrderUser() {
        return orderUser;
    }

    private void setOrderId (int _orderId) {
        this.orderId = _orderId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

}
