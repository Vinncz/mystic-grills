package test.src.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import database_access.ConnectionMaster;
import models.helpers.ConnectsWithDatabase;

public class MenuItem extends ConnectsWithDatabase{
    private static Connection db = ConnectionMaster.getConnection();
    private static final String TABLE_NAME = "menu_items";
    // private static Integer cumulativeMenuItemId = 0;

    private Integer menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private Integer menuItemPrice;

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
    public static MenuItem getMenuItemById (Integer _menuItemId) {

        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        MenuItem target = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(query);
            ps.setInt(1, _menuItemId);

            ResultSet res = ps.executeQuery();

            while ( res.next() ) {
                target = new MenuItem();

                target.setMenuItemId            (res.getInt("id"));
                target.setMenuItemName          (res.getString("name"));
                target.setMenuItemDescription   (res.getString("description"));
                target.setMenuItemPrice         (res.getInt("price"));
            }

            res.close();
            ps.close();

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

    public static ArrayList<MenuItem> getAllMenuItems () {

        final String query = "SELECT * FROM " + TABLE_NAME;

        ArrayList<MenuItem> target = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet res;

        try {
            ps = db.prepareStatement(query);
            res = ps.executeQuery();

            while (res.next()){
                MenuItem mi = new MenuItem();

                mi.setMenuItemId            (res.getInt("id"));
                mi.setMenuItemName          (res.getString("name"));
                mi.setMenuItemDescription   (res.getString("description"));
                mi.setMenuItemPrice         (res.getInt("price"));

                target.add(mi);
            }

            res.close();
            ps.close();

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
    public static MenuItem createMenuItem(String _menuItemName, String _menuItemDescription, Integer _menuItemPrice) {

        final String query = "INSERT INTO " + TABLE_NAME + " (name, description, price) VALUE (?, ?, ?)";

        MenuItem mi = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, _menuItemName);
            ps.setString(2, _menuItemDescription);
            ps.setInt(3, _menuItemPrice);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("createMenuItem", ps.toString(), rowsAffected);

            if( !Commit(db, ps.toString()) )
                throw new Exception();

            /* If a new order is successfully made, then you can safely insert its order_items */
            ResultSet generatedKeys  = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                int generatedId = generatedKeys.getInt(1);

                mi = new MenuItem();

                mi.setMenuItemId(generatedId);
                mi.menuItemName = _menuItemName;
                mi.menuItemDescription = _menuItemDescription;
                mi.menuItemPrice = _menuItemPrice;
            }

            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return mi;
    }

    /*
     * PATCH
     */
    public static Boolean updateMenuItem (Integer _menuItemId, String _menuItemName, String _menuItemDescription, Integer _menuItemPrice) {

        final String query = "UPDATE " + TABLE_NAME + " SET name = ?, description = ?, price = ? WHERE id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(query);

            ps.setString(1, _menuItemName);
            ps.setString(2, _menuItemDescription);
            ps.setInt(3, _menuItemPrice);
            ps.setInt(4, _menuItemId);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("updateMenuItem", ps.toString(), rowsAffected);

            if ( !Commit(db, ps.toString()) ) {
                throw new Exception();
            }

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
    public static Boolean deleteMenuItem (Integer _menuItemId) {

        final String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(query);
            ps.setInt(1, _menuItemId);

            int rowsAffected = ps.executeUpdate();

            SummarizeDatabaseExecution("deleteMenuItem", ps.toString(), rowsAffected);

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
    public void setMenuItemId(Integer _id){
        this.menuItemId = _id;
    }

    public Integer getMenuItemId(){
        return this.menuItemId;
    }

    public void setMenuItemName(String _menuItemName){
        this.menuItemName = _menuItemName;
    }

    public String getMenuItemName(){
        return this.menuItemName;
    }

    public void setMenuItemDescription(String _menuItemDescription){
        this.menuItemDescription = _menuItemDescription;
    }

    public String getMenuItemDescription(){
        return this.menuItemDescription;
    }

    public void setMenuItemPrice(Integer _menuItemPrice){
        this.menuItemPrice = _menuItemPrice;
    }

    public Integer getMenuItemPrice(){
        return this.menuItemPrice;
    }
}
