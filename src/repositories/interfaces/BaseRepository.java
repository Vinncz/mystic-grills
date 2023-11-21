package repositories.interfaces;

// TODO refactor methods which uses executeUpdate into two seperate methods

import values.DATABASE_MODIFICATION_POLICY;
import values.SYSTEM_PROPERTIES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import repositories.helpers.DatabaseExceptionExplainer;

public abstract class BaseRepository <T> {

    public final int MAXIMUM_ROW_FOR_MODIFICATION = DATABASE_MODIFICATION_POLICY.MAXIMUM_ROW_FOR_MODIFICATION.value;

    /**
     * Parses a concrete object from a given result set, that may contain object's attributes.
     *
     * Every implementation of this method should handle all of the error-handling task, itself.
     *
     * @return An optional object, who may contain a concrete object whose attribute has been filled with the data inside result set.
     */
    public abstract Optional<T> parse (ResultSet _resultSetThatMayContainObject);

    /**
     * Parses many concrete object from a given result set, that may contain the attributes needed to parse each of the objects.
     *
     * If the given result set does not contain any data, it will return an empty list.
     *
     * @return A list of concrete object, or an empty list.
     */
    public abstract ArrayList<T> parseMany (ResultSet _resultSetThatMayContainObject);

    /**
     * Retrieves all data of an associated concrete object, whose id matched the id given through parameter.
     *
     * @return The concrete object whose attribute has been filled with the data given.
     */
    public abstract Optional<T> getById (Integer _id);

    /**
     * Provides a way to retrieve all data inside the database
     *
     * @return All of the data from the database, that has been parsed into a list of concrete object.
     */
    public abstract ArrayList<T> getAll ();

    /**
     * Provides a way to insert a concrete object into the database.
     *
     * @return The same object you passed on, but with its id attribute filled.
     */
    public abstract T post (T _object);

    /**
     * Provides a way to update a concrete object in the database
     *
     * @return Confirmation of whether the modification operation is successful or not.
     */
    public abstract Boolean put (T _replacementObject);

    /**
     * Provides a way to delete a concrete object from the database
     *
     * @return Confirmation of whether the deletion operation is successful or not.
     */
    public abstract Boolean delete (Integer _id);

    public class executeQueryReturnDatatypes {
        public PreparedStatement ps;
        public ResultSet rs;

        public executeQueryReturnDatatypes (PreparedStatement _ps, ResultSet _rs) {
            this.ps = _ps;
            this.rs = _rs;
        }
    }

    /**
     * Simplifying the way you execute your query. You won't have to set the PreparedStatement's parameters by yourself in every method
     *
     * @param _db • Connection object that connects with the database
     * @param _query • Query string that is either 'INSERT' or 'UPDATE' or 'DELETE' operation
     * @param _params • Parameters to be set into the query
     * @return An executeQueryReturnDatatypes object which contains: (1) PreparedStatement object to be closed, and (2) ResultSet object that contains the retrieved data
     * @throws SQLException
     */
    public executeQueryReturnDatatypes executeQuery (Connection _db, String _query, Object... _params) throws SQLException {
        PreparedStatement ps = _db.prepareStatement(_query);

        for (int i = 1; i <= _params.length; i++)
            ps.setObject(i, _params[i - 1]);

        return new executeQueryReturnDatatypes(ps, ps.executeQuery());
    }

    /**
     * A datatype class, which contains how many rows were affected, and what was the generated id that database generated.
     *
     * @attribute rowsAffected
     * @attribute generatedId
     */
    public class executeUpdateReturnDatatypes {
        public Integer rowsAffected;
        public Integer generatedId;

        public executeUpdateReturnDatatypes (Integer _rowsAffected, Integer _generatedId) {
            this.rowsAffected = _rowsAffected;
            this.generatedId = _generatedId;
        }
    }

    /**
     * Simplifying the way you do your modification to a database. You won't have to set the PreparedStatement's parameters by yourself in every method
     *
     * @param _db • Connection object that connects with the database
     * @param _query • Query string that is either 'INSERT' or 'UPDATE' or 'DELETE' operation
     * @param _params • Parameters to be set into the query
     * @return An executeUpdateReturnDatatypes object which contains: (1) the number of rowsAffected, and (2) the generatedId that is created by the database (defaults to -1 if there are no generatedId created).
     * @throws SQLException
     */
    public executeUpdateReturnDatatypes executeUpdate (Connection _db, String _query, Object... _params) throws SQLException {
        PreparedStatement ps = _db.prepareStatement(_query, Statement.RETURN_GENERATED_KEYS);

        for (int i = 1; i <= _params.length; i++)
            ps.setObject(i, _params[i - 1]);

        Integer rowsAffected = ps.executeUpdate();
        Integer generatedId  = -1;

        ResultSet res = ps.getGeneratedKeys();
        if (res.next()) {
            generatedId = res.getInt(1);
        }

        ps.close();

        return new executeUpdateReturnDatatypes(rowsAffected, generatedId);
    }

    public Boolean save (Connection db) {
        try {
            db.commit();
            return true;

        } catch (SQLException _unableToCommit) {
            DatabaseExceptionExplainer.explainCommitFault(_unableToCommit);
            return false;

        }
    }

    public Boolean rollback (Connection db) {
        try {
            db.rollback();
            return true;

        } catch (SQLException _unableToRollback) {
            DatabaseExceptionExplainer.explainRollbackFault(_unableToRollback);
            return false;

        }
    }

}
