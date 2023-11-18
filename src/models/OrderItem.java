package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database_access.ConnectionMaster;
import models.helpers.ConnectsWithDatabase;

public class OrderItem extends ConnectsWithDatabase {

    private static Connection db = ConnectionMaster.getConnection();
    private static final String TABLE_NAME = "order_items";

    private Integer orderId;
    private MenuItem menuItem;
    private Integer quantity;





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
    public static ArrayList<OrderItem> getAllOrderItemsByOrderId (Integer _orderId){

        /* TEMPLATE QUERY SHOULD BE FINAL */
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " oi WHERE oi.order_id = ?";

        /* PREPARE CONTAINER FOR THE QUERIED DATA */
        ArrayList<OrderItem> target = new ArrayList<>();
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
            ps.setInt(1, _orderId);

            ResultSet res = ps.executeQuery();

            /* As long as MySQL returns a non-empty query results, do the following */
            while ( res.next() ) {

                /* Make an empty object first */
                OrderItem oi = new OrderItem();

                /* Outsource related objects from its parent class' methods */
                MenuItem mi = MenuItem.getMenuItemById(res.getInt("menu_item_id"));

                oi.setOrderId     (res.getInt("id"));
                oi.setMenuItem    (mi);
                oi.setQuantity    (res.getInt("quantity"));

                target.add(oi);
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

    /*
     * POST
     */
    public static OrderItem createOrderItem(Integer _orderId, MenuItem _menuItem, Integer _quantity){

        /* TEMPLATE QUERY SHOULD BE FINAL */
        final String QUERY = "INSERT INTO " + TABLE_NAME + " (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";

        OrderItem oi = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);

            /* Fill the arguments with the provided parameters */
            ps.setInt(1, _orderId);
            ps.setInt(2, _menuItem.getMenuItemId());
            ps.setInt(3, _quantity);

            /* For INSERTION/UPDATION/DELETION, we use executeUpdate() instead of executeQuery */
            int rowsAffected = ps.executeUpdate();

            /* ALWAYS summarize your query execution INSIDE insert/update/delete methods */
            SummarizeDatabaseExecution("createOrderItem", ps.toString(), rowsAffected);

            /* If database cannot commit to a transaction, stop any object creation */
            if ( !Commit(db, ps.toString()) )
                throw new Exception();

            /* If the code manages to reach this point, then we can be sure that there are no errors present */
            oi = new OrderItem();
            oi.setOrderId(_orderId);
            oi.setMenuItem(_menuItem);
            oi.setQuantity(_quantity);

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

        return oi;
    }

    /*
     * PATCH
     */
    public static boolean updateOrderItem(Integer _orderId, MenuItem _menuItem, Integer _quantity){
        final String QUERY = "UPDATE " + TABLE_NAME + " oi SET oi.menu_item_id = ?, oi.quantity = ? WHERE oi.order_id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(QUERY);

            if (_quantity <= 0) {
                System.out.println("\n\nQuantity is below 0. Deleting menuItem" + _menuItem.getMenuItemId() + " from orderId " + _orderId + ".\n");
                return deleteOrderItem(_orderId, _menuItem.getMenuItemId());

            }

            ps.setInt(1, _menuItem.getMenuItemId());
            ps.setInt(2, _quantity);
            ps.setInt(3, _orderId);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("updateOrderItem", ps.toString(), rowsAffected);

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
    public static boolean deleteOrderItem(Integer _orderId, Integer _menuItemId){
        final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE order_id = ? AND menu_item_id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(QUERY);

            ps.setInt(1, _orderId);
            ps.setInt(2, _menuItemId);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("deleteOrderItem", ps.toString(), rowsAffected);

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
    public void setOrderId (Integer _orderId) {
        this.orderId = _orderId;
    }

    public Integer getOrderId(){
        return this.orderId;
    }

    public void setMenuItem(MenuItem _menuItem){
        this.menuItem = _menuItem;
    }

    public MenuItem getMenuItem(){
        return this.menuItem;
    }

    public void setQuantity(Integer _quantity){
        this.quantity = _quantity;
    }

    public Integer getQuantity(){
        return this.quantity;
    }
}
