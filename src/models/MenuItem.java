package models;

// TODO remove every database-accessing methods

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import models.interfaces.Parsable;
import repositories.helpers.DatabaseAccessor;
import values.SYSTEM_PROPERTIES;
import values.strings.DatabaseMessages;

public class MenuItem extends DatabaseAccessor implements Parsable<MenuItem> {

    private static final String TABLE_NAME = SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value;

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


    @Override
    public MenuItem parse (ResultSet _rs) throws SQLException {
        MenuItem mi = new MenuItem();

        while ( _rs.next() ) {
            mi.setMenuItemId            (_rs.getInt("id"));
            mi.setMenuItemName          (_rs.getString("name"));
            mi.setMenuItemDescription   (_rs.getString("description"));
            mi.setMenuItemPrice         (_rs.getInt("price"));
        }

        return mi;
    }

    @Override
    public ArrayList<MenuItem> parseMultiple (ResultSet _rs) throws SQLException {
        ArrayList<MenuItem> parsedMenuItems = new ArrayList<>();

        while ( _rs.next() ) {
            MenuItem mi = new MenuItem();

            mi.setMenuItemId            (_rs.getInt("id"));
            mi.setMenuItemName          (_rs.getString("name"));
            mi.setMenuItemDescription   (_rs.getString("description"));
            mi.setMenuItemPrice         (_rs.getInt("price"));

            parsedMenuItems.add(mi);
        }

        return parsedMenuItems;
    }

    /*
     * GET
     */
    public static MenuItem getMenuItemById (Integer _menuItemId) {

        final String query = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
        PreparedStatement ps = null;
        MenuItem target = null;

        try {
            ResultSet res = executeQuery(query, _menuItemId);


            res.close();

        } catch (SQLException problemDuringQueryExecution) {

            explain(
                problemDuringQueryExecution,
                (ps != null) ?
                    ps.toString() :
                    DatabaseMessages.EMPTY_PREPARED_STATEMENT.value
            );

        } catch (IllegalArgumentException problemDuringEnumeralAssignment) {

            System.out.println(
                problemDuringEnumeralAssignment.getMessage() != null ?
                    problemDuringEnumeralAssignment.getMessage() :
                    DatabaseMessages.PREPARED_STATEMENT_ILLEGAL_ARGUMENT
            );

        } catch (Exception unanticipatedProblem) {

            throw new RuntimeException(
                unanticipatedProblem.getMessage() != null ?
                unanticipatedProblem.getMessage() :
                DatabaseMessages.GENERIC_UNASSOCIATED_EXCEPTION.value
            );

        }

        return target;
    }

    public static ArrayList<MenuItem> getAllMenuItems () {

        final String query = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<MenuItem> target = new ArrayList<>();
        PreparedStatement   ps = null;
        ResultSet           res;

        try {
            ps  = db.prepareStatement(query);
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

        } catch (SQLException problemDuringQueryExecution) {

            explain(
                problemDuringQueryExecution,
                (ps != null) ?
                    ps.toString() :
                    DatabaseMessages.EMPTY_PREPARED_STATEMENT.value
            );

        } catch (IllegalArgumentException problemDuringEnumeralAssignment) {

            System.out.println(
                problemDuringEnumeralAssignment.getMessage() != null ?
                    problemDuringEnumeralAssignment.getMessage() :
                    DatabaseMessages.PREPARED_STATEMENT_ILLEGAL_ARGUMENT
            );

        } catch (Exception unanticipatedProblem) {

            throw new RuntimeException(
                unanticipatedProblem.getMessage() != null ?
                unanticipatedProblem.getMessage() :
                DatabaseMessages.GENERIC_UNASSOCIATED_EXCEPTION.value
            );

        }

        return target;
    }

    /*
     * POST
     */
    public static MenuItem createMenuItem(String _menuItemName, String _menuItemDescription, Integer _menuItemPrice) {

        final String query = String.format("INSERT INTO %s (name, description, price) VALUE (?, ?, ?)", TABLE_NAME);

        MenuItem          mi = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, _menuItemName);
            ps.setString(2, _menuItemDescription);
            ps.setInt   (3, _menuItemPrice);

            int rowsAffected = ps.executeUpdate();
            summarizeDatabaseExecution("createMenuItem", ps.toString(), rowsAffected);

            if( !commit(db, ps.toString()) )
                throw new Exception();

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
            explain(
                e,
                (ps != null) ?
                    ps.toString() :
                    DatabaseMessages.EMPTY_PREPARED_STATEMENT.value
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                e.getMessage() != null ?
                    e.getMessage() :
                    DatabaseMessages.PREPARED_STATEMENT_ILLEGAL_ARGUMENT
            );

        } catch (Exception e) {
            throw new RuntimeException(
                e.getMessage() != null ?
                e.getMessage() :
                DatabaseMessages.GENERIC_UNASSOCIATED_EXCEPTION.value
            );

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
            summarizeDatabaseExecution("updateMenuItem", ps.toString(), rowsAffected);

            if ( !commit(db, ps.toString()) ) {
                throw new Exception();
            }

            successful = true;
            ps.close();

        } catch (SQLException e) {
            explain(
                e,
                (ps != null) ?
                    ps.toString() :
                    DatabaseMessages.EMPTY_PREPARED_STATEMENT.value
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                e.getMessage() != null ?
                    e.getMessage() :
                    DatabaseMessages.PREPARED_STATEMENT_ILLEGAL_ARGUMENT
            );

        } catch (Exception e) {
            throw new RuntimeException(
                e.getMessage() != null ?
                e.getMessage() :
                DatabaseMessages.GENERIC_UNASSOCIATED_EXCEPTION.value
            );

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

            summarizeDatabaseExecution("deleteMenuItem", ps.toString(), rowsAffected);

            if ( !commit(db, ps.toString()) )
                throw new Exception();

            successful = true;
            ps.close();

        } catch (SQLException e) {
            explain(
                e,
                (ps != null) ?
                    ps.toString() :
                    DatabaseMessages.EMPTY_PREPARED_STATEMENT.value
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                e.getMessage() != null ?
                    e.getMessage() :
                    DatabaseMessages.PREPARED_STATEMENT_ILLEGAL_ARGUMENT
            );

        } catch (Exception e) {
            throw new RuntimeException(
                e.getMessage() != null ?
                e.getMessage() :
                DatabaseMessages.GENERIC_UNASSOCIATED_EXCEPTION.value
            );

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
