package repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import database_access.ConnectionMaster;
import exceptions.DatabaseModificationPolicyViolatedException;
import models.MenuItem;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class MenuItemRepository extends BaseRepository<MenuItem> {

    private Connection db;
    private static final String TABLE_NAME = SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value;

    public MenuItemRepository () {
        this.db = ConnectionMaster.getConnection();
    }

    private MenuItem attachAttribute (Integer _id, String _name, String _description, Integer _price) {
        MenuItem moldObject = new MenuItem();

        moldObject.setMenuItemId          (_id);
        moldObject.setMenuItemName        (_name);
        moldObject.setMenuItemDescription (_description);
        moldObject.setMenuItemPrice       (_price);

        return moldObject;
    }

    @Override
    public Optional<MenuItem> parse (ResultSet _resultSetThatMayContainObject) {
        Optional<MenuItem> parsedObject = Optional.empty();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            if (res.next()) {
                MenuItem moldObject = attachAttribute(
                                          res.getInt("id"),
                                          res.getString("name"),
                                          res.getString("description"),
                                          res.getInt("price")
                                      );

                parsedObject = Optional.of(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (NullPointerException _moldObjectIsNull) {
            DatabaseExceptionExplainer.explainMoldObjectIsNull(_moldObjectIsNull);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObject;
    }

    @Override
    public ArrayList<MenuItem> parseMany (ResultSet _resultSetThatMayContainObject) {
        ArrayList<MenuItem> parsedObjects = new ArrayList<>();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            while (res.next()) {
                MenuItem moldObject = attachAttribute(
                                          res.getInt("id"),
                                          res.getString("name"),
                                          res.getString("description"),
                                          res.getInt("price")
                                      );

                parsedObjects.add(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObjects;
    }

    @Override
    public Optional<MenuItem> getById (Integer _id) {
        final String query = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
        Optional<MenuItem> retrievedObject = Optional.empty();

        try {
            BaseRepository<MenuItem>.executeQueryReturnDatatypes report = executeQuery(db, query, _id);
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

    @Override
    public ArrayList<MenuItem> getAll () {
        final String query = String.format("SELECT * FROM %s", TABLE_NAME);
        ArrayList<MenuItem> retrievedObjects = new ArrayList<>();

        try {
            BaseRepository<MenuItem>.executeQueryReturnDatatypes report = executeQuery(db, query);
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

    @Override
    public MenuItem post (MenuItem _object) {
        final String query = String.format("INSERT INTO %s (name, description, price) VALUES (?, ?, ?)", TABLE_NAME);

        try {
            BaseRepository<MenuItem>.executeUpdateReturnDatatypes insertReport = executeUpdate(
                db,
                query,

                _object.getMenuItemName(),
                _object.getMenuItemDescription(),
                _object.getMenuItemPrice()
            );

            Integer generatedId = insertReport.getGeneratedId();
            _object.setMenuItemId(generatedId);

            Integer rowsAffected = insertReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                save(db);

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _maximumModifiableRowViolated) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_maximumModifiableRowViolated);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return _object;
    }

    @Override
    public Boolean put (MenuItem _replacementObject) {
        final String query = String.format("UPDATE %s SET name = ?, description = ?, price = ? WHERE id = ?", TABLE_NAME);

        try {
            BaseRepository<MenuItem>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                query,

                _replacementObject.getMenuItemName(),
                _replacementObject.getMenuItemDescription(),
                _replacementObject.getMenuItemPrice(),

                _replacementObject.getMenuItemId()
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) ) {
                save(db);
                return true;

            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _maximumModifiableRowViolated) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_maximumModifiableRowViolated);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }

    @Override
    public Boolean delete (Integer _id) {
        final String query = String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);

        try {
            BaseRepository<MenuItem>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                query,

                _id
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) ) {
                save(db);
                return true;

            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _maximumModifiableRowViolated) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_maximumModifiableRowViolated);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }

}
