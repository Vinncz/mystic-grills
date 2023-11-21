package database_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import values.SYSTEM_PROPERTIES;
import values.strings.DatabaseMessages;

public class ConnectionMaster {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbaseURL = SYSTEM_PROPERTIES.DATABASE_URL.value;
            String username = SYSTEM_PROPERTIES.DATABASE_USERNAME.value;
            String password = SYSTEM_PROPERTIES.DATABASE_PASSWORD.value;

            connection = DriverManager.getConnection(dbaseURL, username, password);
            setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(DatabaseMessages.CONNECTION_FAILED.value);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(DatabaseMessages.DRIVER_NOT_FOUND.value);

        }

    }

    private static void setAutoCommit (boolean _b) {
        try {
            connection.setAutoCommit(_b);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(DatabaseMessages.AUTOCOMMIT_TO_FALSE_FAILURE.value);

        }

    }

    public static Connection getConnection () {
        return connection;

    }

    public static void closeConnection () {
        try {
            if ( !(connection == null || connection.isClosed()) )
                connection.close();

            System.out.println(DatabaseMessages.CONNECTION_CLOSED.value);
            
        } catch (Exception e) {}

    }
}
