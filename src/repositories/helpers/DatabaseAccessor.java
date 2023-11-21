package repositories.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database_access.ConnectionMaster;

public abstract class DatabaseAccessor {

    protected static Connection db = ConnectionMaster.getConnection();
    private static int errorCount = 1;

    protected static ResultSet executeQuery (String _query, Object... _params) throws SQLException {

        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(_query);

            for (int i = 1; i <= _params.length; i++)
                ps.setObject(i, _params[i]);

            return ps.executeQuery();

        } finally {
            if ( !ps.isClosed() && ps != null )
                ps.close();

        }

    }

    protected static Boolean commit (Connection _db, String _query) {
        try {
            _db.commit();
            return true;

        } catch (SQLException e) {
            explain(e, _query);

            try {
                _db.rollback();

            } catch (SQLException rollbackError) {
                explain(rollbackError, "FATAL ERROR, ROLLBACK FAILED!");

            }

            return false;

        }
    }

    /**
     * Prints out a summary of query execution on the terminal. This includes how many rows were affected, what exactly was queried to the database, and tells you whether your query will be commited or be rolledback.
     * @param _methodName
     * @param _query
     * @param _rowsAffected
     * @throws IllegalArgumentException
     */
    protected static void summarizeDatabaseExecution (String _methodName, String _query, Integer _rowsAffected) throws IllegalArgumentException {
        System.out.println("");

        System.out.println("While the " + _methodName + "() method queried");
        System.out.println("    '" + sliceQuery(_query) + "',");
        System.out.println("The following were observed:");
        if (_rowsAffected > 1) {
            System.out.println("    A total of " + _rowsAffected + " row(s) are going to be affected.");
            System.out.println("To prevent accidental database damage, the following were performed:");

            /* YOU MUST THROW AN EXCEPTION TO PREVENT THE DATABASE FROM COMMITING */
            throw new IllegalArgumentException("    This query will not be commited into the database. No rows were updated, inserted, nor deleted.\n");

        } else {
            System.out.println("    " + _rowsAffected + " row(s) were affected.");

        }

        System.out.println("");
    }

    protected static void explain (SQLException _e, String _queryOrErrorMessage) {
        System.out.println("");
        System.out.println("(!) ERROR #" + errorCount++ + " ___________________________");
        System.out.println("The database indicated that there has been a problem while querying");
        System.out.println("    '" + sliceQuery(_queryOrErrorMessage) + "'.");
        System.out.println("The following were reported:");
        System.out.println("    ERROR CODE:\t" + _e.getErrorCode());
        System.out.println("    SQL STATE :\t" + _e.getSQLState());
        System.out.println("    MESSAGE   :\t" + _e.getMessage());
        System.out.println("");
    }

    private static String sliceQuery (String _query) {
        int strlen = _query.length();
        int beginPos = _query.indexOf(":") + 2;

        if ( beginPos > strlen ) {
            return _query.substring(_query.indexOf(":") + 2);

        } else {
            return _query;

        }
    }

}
