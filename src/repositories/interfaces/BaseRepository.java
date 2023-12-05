package repositories.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import database_access.ConnectionMaster;
import exceptions.DatabaseModificationPolicyViolatedException;
import repositories.helpers.DatabaseExceptionExplainer;
import values.DATABASE_MODIFICATION_POLICY;
import values.SYSTEM_PROPERTIES;

/**
 * A superclass on which every repository classes should be based on.
 * This class encompasses mostly everything about things that a repository class should be responsible of.
 *
 * <br /> <br />
 *
 * However, it is recommended to implement the following method, even though it is not listed as an official method of BaseRepository.
 *
 * <br /> <br />
 *
 * <pre> private T attachAttribute (Object... _attributes) {} </pre>
 *
 * <br /> <br />
 *
 * Method above handles the assignment of attributes into an object.
 * Add the <pre>throws IllegalArgumentException</pre> clause to said function, if and only if the associated model has enumeral for attribute.
 */
public abstract class BaseRepository <T> {

    protected final int MAXIMUM_ROW_FOR_MODIFICATION = DATABASE_MODIFICATION_POLICY.MAXIMUM_ROW_FOR_MODIFICATION.value;
    protected final boolean LOG_DATABASE_TRANSACTION = SYSTEM_PROPERTIES.ACTIVATE_LOG.value.equals("true");

    protected Connection db;
    protected String TABLE_NAME;

    protected String GET_BY_ID_QUERY;
    protected String GET_ALL_QUERY;
    protected String POST_QUERY;
    protected String PUT_QUERY;
    protected String DELETE_QUERY;

    /**
     * Sets up the required variable needed to create, retrieve, modify, and delete data from database.
     * @param _tableName
     * @param _getByIdQuery
     * @param _getAllQuery
     * @param _postQuery
     * @param _putQuery
     * @param _deleteQuery
     */
    public BaseRepository (String _tableName, String _getByIdQuery, String _getAllQuery, String _postQuery, String _putQuery, String _deleteQuery) {
        this.db              = ConnectionMaster.getConnection();

        this.TABLE_NAME      = _tableName;

        this.GET_BY_ID_QUERY = _getByIdQuery;
        this.GET_ALL_QUERY   = _getAllQuery;
        this.POST_QUERY      = _postQuery;
        this.PUT_QUERY       = _putQuery;
        this.DELETE_QUERY    = _deleteQuery;
    }

    /**
     * Retrieves the id from the given parameter.
     *
     * @param _object • Object whose id is to be extracted.
     * @return The id of said object.
     */
    protected abstract Integer getObjectId (T _object);

    /**
     * Sets the id of an object.
     *
     * @param _mi • The object whose id is to be filled.
     * @param _id • The id to be filled.
     * @return the object itself, with its id attribute filled
     */
    protected abstract T setObjectId (T _object, Integer _id);





    /**
     * A helper method which attaches arguments (parameters) into a given {@code PreparedStatement} object.
     *
     * @param ps • The PreparedStatement object, which will later be attached by parameters.
     * @param _params • Your parameters of choice.
     * @return String of PreparedStatement, with its parameters attached
     * @throws SQLException
     */
    private String attachParameters(PreparedStatement ps, Object... _params) throws SQLException {
        String copyOfStatement = ps.toString().substring(43);

        for (int i = 1; i <= _params.length; i++) {
            ps.setObject(i, _params[i - 1]);
            copyOfStatement = copyOfStatement.replaceFirst("\\*\\*\\sNOT\\sSPECIFIED\\s\\*\\*", String.format("'%s'", _params[i-1].toString()));

        }

        return copyOfStatement;
    }

    /**
     * Parses a concrete object from a given result set, that may or may not contain object's attributes.
     *
     * Every implementation of this method should handle all of the error-handling task itself.
     *
     * For best practice, create a method named 'attachAttribute', with the parameters of said object's attributes.
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
     * Unparses a concrete object's attribute from the given parameter into an array of Objects.
     * <b> Do note </b> that the unparsed attribute's sequence MUST MATCH with the declared sequence of COLUMN, of a table.
     * <br></br>
     * ㅤ
     * <br></br>
     *     <b> For example: </b> let there be a table that is declared as follows: {@code (name VARCHAR(64), price INT)}.
     * <br></br>
     *     Therefore, in your implementation for this method, you should unparse the attribute as the following sequence:
     * <br></br>
     * <pre> return new Object [] { _object.getName(), _object.getPrice() } </pre>
     *
     *
     * @param _object • Object to be unparsed.
     * @return Array of objects that vary in size, according to the passed parameter's datatype.
     */
    public abstract Object[] unparseAttributes (T _object);





    /**
     * Retrieves all data of an associated concrete object, whose id matched the id given through parameter.
     *
     * @param _id • The id of which object you wish to retrieve
     * @return The concrete object whose attribute has been filled with the data given.
     */
    public Optional<T> getById (Integer _id) {
        Optional<T> retrievedObject = Optional.empty();

        try {
            BaseRepository<T>.executeQueryReturnDatatypes report = executeQuery(db, GET_BY_ID_QUERY, _id);
            retrievedObject = parse(report.getResultSet());

            report.getResultSet().close();
            report.getPreparedStatement().close();

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return retrievedObject;
    }

    /**
     * Provides a way to retrieve all data inside the database
     *
     * @return All of the data from the database, that has been parsed into a list of concrete object.
     */
    public ArrayList<T> getAll () {
        ArrayList<T> retrievedObjects = new ArrayList<>();

        try {
            BaseRepository<T>.executeQueryReturnDatatypes report = executeQuery(db, GET_ALL_QUERY);
            retrievedObjects = parseMany(report.getResultSet());

            report.getResultSet().close();
            report.getPreparedStatement().close();

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return retrievedObjects;
    }

    /**
     * Provides a way to insert a concrete object into the database.
     *
     * @param _objectToBeInserted • The object you wish to insert into the database. Repository classes will handle the unwraping of the object's attribute.
     * @return The same object you passed on, but with its id attribute filled.
     */
    public T post (T _objectToBeInserted) {
        try {
            BaseRepository<T>.executeUpdateReturnDatatypes insertReport = executeUpdate(
                db,
                POST_QUERY,

                unparseAttributes(_objectToBeInserted)
            );

            Integer generatedId = insertReport.getGeneratedId();
            _objectToBeInserted = setObjectId(_objectToBeInserted, generatedId);

            Integer rowsAffected = insertReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                save(db);

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return _objectToBeInserted;
    }

    /**
     * Provides a way to update a concrete object in the database.
     *
     * @param _replacementObject • The replacement object, whose attributes will override all the old data inside the database. Make sure for it to have its id attribute filled!
     * @return Confirmation of whether the modification operation is successful or not.
     */
    public Boolean put (T _replacementObject) {
        try {
            Object[] attributes = unparseAttributes(_replacementObject);
            Object[] combinedParams = new Object[attributes.length + 1];

            System.arraycopy(attributes, 0, combinedParams, 0, attributes.length);
            combinedParams[attributes.length] = getObjectId(_replacementObject);

            BaseRepository<T>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                PUT_QUERY,

                combinedParams
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                return save(db);

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }

    /**
     * Provides a way to delete a concrete object from the database
     *
     * @param _idOfAnObjectToBeDeleted • The id of which object you wish to remove
     * @return Confirmation of whether the deletion operation is successful or not.
     */
    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        try {
            BaseRepository<T>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                DELETE_QUERY,

                _idOfAnObjectToBeDeleted
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                return save(db);

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }





    /**
     * A datatype class, which contains how many rows were affected, and what was the generated id that database generated.
     *
     * @attribute rowsAffected
     * @attribute generatedId
     */
    public class executeQueryReturnDatatypes {
        private PreparedStatement ps;
        private ResultSet rs;

        public executeQueryReturnDatatypes (PreparedStatement _ps, ResultSet _rs) {
            this.ps = _ps;
            this.rs = _rs;
        }

        public PreparedStatement getPreparedStatement() {
            return ps;
        }

        public ResultSet getResultSet() {
            return rs;
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

        String finalQuery = attachParameters(ps, _params);
        if ( LOG_DATABASE_TRANSACTION ) {
            log(finalQuery);
        }

        return new executeQueryReturnDatatypes(ps, ps.executeQuery());
    }

    /**
     * A datatype class, which contains how many rows were affected, and what was the generated id that database generated.
     *
     * @attribute rowsAffected
     * @attribute generatedId
     */
    public class executeUpdateReturnDatatypes {
        private Integer rowsAffected;
        private Integer generatedId;

        public executeUpdateReturnDatatypes (Integer _rowsAffected, Integer _generatedId) {
            this.rowsAffected = _rowsAffected;
            this.generatedId = _generatedId;
        }

        public Integer getRowsAffected() {
            return rowsAffected;
        }

        public Integer getGeneratedId() {
            return generatedId;
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

        String finalQuery = attachParameters(ps, _params);
        if ( LOG_DATABASE_TRANSACTION ) {
            log(finalQuery);
        }

        Integer rowsAffected = ps.executeUpdate();
        Integer generatedId  = -1;

        ResultSet res = ps.getGeneratedKeys();
        if (res.next()) {
            generatedId = res.getInt(1);
        }

        ps.close();

        return new executeUpdateReturnDatatypes(rowsAffected, generatedId);
    }





    /**
     * Throws a DatabaseModificationPolicyViolatedException when affected rows is larger than a treshold; otherwise always return true.
     * @param rowsAffected
     * @throws DatabaseModificationPolicyViolatedException
     */
    public Boolean modificationFollowsDatabasePolicy (Integer rowsAffected) throws DatabaseModificationPolicyViolatedException {
        if (rowsAffected > MAXIMUM_ROW_FOR_MODIFICATION)
            throw new DatabaseModificationPolicyViolatedException();

        return true;
    }





    /**
     * Saves any changes made to the database.
     *
     * @param db • Database connection
     * @return Confirmation of whether the commit operation is successful or not.
     */
    public Boolean save (Connection db) {
        try {
            db.commit();
            return true;

        } catch (SQLException _unableToCommit) {
            DatabaseExceptionExplainer.explainCommitFault(_unableToCommit);
            return false;

        }
    }

    /**
     * Restores any changes made to the database SINCE the last commited modification.
     *
     * @param db • Database connection
     * @return Confirmation of whether the rollback operation is successful or not
     */
    public Boolean rollback (Connection db) {
        try {
            db.rollback();
            return true;

        } catch (SQLException _unableToRollback) {
            DatabaseExceptionExplainer.explainRollbackFault(_unableToRollback);
            return false;

        }
    }

    /**
     * The helper method whose job is to print out what's being queried to the database.
     * @param _databaseQuery
     */
    private void log (String _databaseQuery) {
        System.out.println("Database executed:\n    " + _databaseQuery);
    }

}
