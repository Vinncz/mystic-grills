package models;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database_access.ConnectionMaster;
import models.helpers.ConnectsWithDatabase;

public class User extends ConnectsWithDatabase {



    public enum UserRole {
        ADMIN,
        CASHIER,
        CHEF,
        CUSTOMER,
        WAITER,
    }

    private static Connection db = ConnectionMaster.getConnection();
    private static final String TABLE_NAME = "users";

    private Integer userId;
    private UserRole userRole;
    private String userName;
    private String userEmail;
    private String userPassword;


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
    public static User getUserById(Integer _userId) {
        final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        User target = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);
            ps.setInt(1, _userId);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                User.UserRole role = UserRole.valueOf(res.getString("role"));

                target = new User();
                target.userId = res.getInt("id");
                target.setUserName(res.getString("name"));
                target.setUserEmail(res.getString("email"));
                target.setUserPassword(res.getString("password"));
                target.setUserRole(role);
            }

            /* Close the uneeded PreparedStatement and ResultSet */
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


    public static ArrayList<User> getAllUsers() {
        final String QUERY = "SELECT * FROM " + TABLE_NAME;

        ArrayList<User> target = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);
            ResultSet res = ps.executeQuery();

            while (res.next()) { // Loop through all rows
                User u = new User();
                User.UserRole role = UserRole.valueOf(res.getString("role"));

                u.userId = res.getInt("id");
                u.setUserName(res.getString("name"));
                u.setUserEmail(res.getString("email"));
                u.setUserPassword(res.getString("password"));
                u.setUserRole(role);

                target.add(u);
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
    public static User createUser(UserRole _userRole, String _userName, String _userEmail, String _userPassword) {

        final String QUERY = "INSERT INTO " + TABLE_NAME + " (name, role, email, password) VALUES (?, ?, ?, ?)";

        User target = null;
        PreparedStatement ps = null;

        try {

            ps = db.prepareStatement(QUERY,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, _userName);
            ps.setString(2, _userRole.toString());
            ps.setString(3, _userEmail);
            ps.setString(4, _userPassword);

            int rowsAffected = ps.executeUpdate();

            SummarizeDatabaseExecution("createUser", ps.toString(), rowsAffected);

            if( !Commit(db, ps.toString()) )
                throw new Exception();


            ResultSet generatedKeys  = ps.getGeneratedKeys();

            if ( generatedKeys.next() ) {
                target = new User();

                target.setUserId(generatedKeys.getInt(1));
                target.setUserRole(_userRole);
                target.setUserName(_userName);
                target.setUserEmail(_userEmail);
                target.setUserPassword(_userPassword);

            }

            // Close resources
            generatedKeys.close();
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
     * DELETE
     */
    public static Boolean deleteUser(Integer _userId) {

        final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {

            // Create a PreparedStatement and execute the query
            ps = db.prepareStatement(QUERY);

            ps.setInt(1, _userId);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("deleteUser", ps.toString(), rowsAffected);

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
     * PATCH
     */
    public static Boolean updateUser(Integer _userId, UserRole _userRole, String _userName, String _userEmail, String _userPassword) {

        final String QUERY = "UPDATE " + TABLE_NAME + " SET name = ?, email = ?, role = ?, password = ? WHERE id = ?";

        PreparedStatement ps = null;
        Boolean successful = false;

        try {

            ps = db.prepareStatement(QUERY);

            ps.setString(1, _userName);
            ps.setString(2, _userEmail);
            ps.setString(3, _userRole.toString());
            ps.setString(4, _userPassword);
            ps.setInt(5, _userId);

            int rowsAffected = ps.executeUpdate();
            SummarizeDatabaseExecution("updateUser", ps.toString(), rowsAffected);

            if ( !Commit(db, ps.toString()) )
                throw new Exception();

            successful = true;
            ps.close();

        }catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return successful;
    }


    /*
     * MISCL
     */
    public static User authenticateUser (String _userEmail, String _userPassword) {
        // User u = new getUserByEmail(_userEmail);
        User target = getUserByEmail(_userEmail);

        if (target != null && target.getUserPassword().equals(_userPassword)) {
            return target;
        }

        return null;
    }






    /*
     * ==========================================================================
     *
     * (!)
     * OUTSIDE OF DIAGRAM-SPECIFIED INSTRUCTION
     *
     * The following are subject to deletion/modification/deprecation
     * should the Lab Assistant forbid.
     *
     * ==========================================================================
     */

     public static User getUserByEmail(String _userEmail) {

        final String QUERY = "SELECT * FROM "  + TABLE_NAME +  " WHERE email = ?";

        User target = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);

            ps.setString(1, _userEmail);

            ResultSet res = ps.executeQuery();

            if (res.next()) {
                target = new User();
                User.UserRole role = UserRole.valueOf(res.getString("role"));

                target.userId = res.getInt("id");
                target.setUserName(res.getString("name"));
                target.setUserEmail(res.getString("email"));
                target.setUserPassword(res.getString("password"));
                target.setUserRole(role);

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
    public Integer getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserId(Integer _id) {
        this.userId = _id;
    }

    public void setUserEmail(String _userEmail) {
        this.userEmail = _userEmail;
    }

    public void setUserName(String _userName) {
        this.userName = _userName;
    }

    public void setUserPassword(String _userPassword) {
        this.userPassword = _userPassword;
    }

    public void setUserRole(UserRole _userRole) {
        this.userRole = _userRole;
    }

}
