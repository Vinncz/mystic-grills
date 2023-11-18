package models.helpers;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectsWithDatabase {

    private static int errorCount = 1;

    /*
     * ERROR TEMPLATE MESSAGES
     */
    protected static final String SQLEXCEPTION_GENERIC_MESSAGE
        = "PreparedStatement is null";
    protected static final String ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE
        = "Illegal argument(s) were received. Check your enumerals assignment or check your query to only modify a single line inside the database.";
    protected static final String EXCEPTION_GENERIC_MESSAGE
        = "An exception was raised, yet it didn't have any identifier nor message associated with its creation.";






    protected static Boolean Commit (Connection db, String _query) {
        try {
            db.commit();
            return true;

        } catch (SQLException e) {
            ExplainSQLError(e, _query);

            try {
                db.rollback();

            } catch (SQLException rollbackError) {
                ExplainSQLError(rollbackError, "FATAL ERROR, ROLLBACK FAILED!");

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
    protected static void SummarizeDatabaseExecution (String _methodName, String _query, Integer _rowsAffected) throws IllegalArgumentException {
        System.out.println("");

        System.out.println("While the " + _methodName + "() method queried");
        System.out.println("    '" + SlicedQuery(_query) + "',");
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

    protected static void ExplainSQLError (SQLException e, String _query) {
        System.out.println("");
        System.out.println("(!) ERROR #" + errorCount++ + " ___________________________");
        System.out.println("The database indicated that there has been a problem while querying");
        System.out.println("    '" + SlicedQuery(_query) + "'.");
        System.out.println("The following were reported:");
        System.out.println("    ERROR CODE:\t" + e.getErrorCode());
        System.out.println("    SQL STATE :\t" + e.getSQLState());
        System.out.println("    MESSAGE   :\t" + e.getMessage());
        System.out.println("");
    }

    private static String SlicedQuery (String _query) {
        return _query.substring(_query.indexOf(":") + 2);
    }

}
