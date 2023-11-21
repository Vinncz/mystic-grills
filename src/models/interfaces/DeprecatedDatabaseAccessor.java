package models.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import values.strings.DatabaseMessages;

@Deprecated
public interface DeprecatedDatabaseAccessor {

    default Boolean commmit (Connection _db, String _query) {

        try {
            _db.commit();
            return true;

        } catch (SQLException commitException) {
            explainSQLError(commitException, _query);

            try {
                _db.rollback();
                System.out.println(DatabaseMessages.ROLLBACK_SUCCESS.value);

            } catch (SQLException rollbackException) {
                System.out.println(DatabaseMessages.ROLLBACK_FAILURE.value);

            }
        }

        return false;
    }

    /**
     * Prints out a summary of query execution on the terminal. This includes how many rows were affected, what exactly was queried to the database, and tells you whether your query will be commited or be rolledback.
     * @param _methodName
     * @param _query
     * @param _rowsAffected
     * @throws IllegalArgumentException
     */
    default void summarizeDatabaseExecution (String _methodName, String _query, Integer _rowsAffected) throws IllegalArgumentException {
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

    // TODO: Change the implementation of logging an SQL Error.
    default void explainSQLError (SQLException _e, String _failedQuery) {
        System.out.println("");
        System.out.println("(!) ERROR ___________________________");
        System.out.println("The database indicated that there has been a problem while querying");
        System.out.println("    '" + sliceQuery(_failedQuery) + "'.");
        System.out.println("The following were reported:");
        System.out.println("    ERROR CODE:\t" + _e.getErrorCode());
        System.out.println("    SQL STATE :\t" + _e.getSQLState());
        System.out.println("    MESSAGE   :\t" + _e.getMessage());
        System.out.println("");
    }

    default String sliceQuery (String _query) {
        return _query.substring(_query.indexOf(":") + 2);
    }
}
