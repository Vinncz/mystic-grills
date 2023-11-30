package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.User;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class UserRepository extends BaseRepository<User> {

    public UserRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("INSERT INTO %s (menu_item_id, quantity) VALUES (?, ?)", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("UPDATE %s SET menu_item_id = ?, quantity = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value)
        );
    }

    public User authenticateUser (String _userEmail, String _userPassword) {;
        Optional<User> target = getUserByEmail(_userEmail);

        if (target.isPresent() && target.get().getUserPassword().equals(_userPassword)) {
            return target.get();
        }

        return null;
    }

    public Optional<User> getUserByEmail(String _userEmail) {
        Optional<User> retrievedObject = Optional.empty();
        final String query = String.format("SELECT * FROM %s WHERE email = ?", TABLE_NAME);

        try {
            BaseRepository<User>.executeQueryReturnDatatypes getByEmailReport = executeQuery(db, query, _userEmail);
            retrievedObject = parse(getByEmailReport.getResultSet());

            getByEmailReport.getResultSet().close();
            getByEmailReport.getPreparedStatement().close();

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

         return retrievedObject;
     }


    @Override
    public Integer getObjectId(User _object) {
        return _object.getUserId();
    }

    @Override
    public User setObjectId(User _object, Integer _id) {
        _object.setUserId(_id);
        return _object;
    }

    private User attachAttribute (Integer _id, String _userRole, String _userName, String _userEmail, String _userPassword) throws IllegalArgumentException {
        User moldObject = new User();

        moldObject.setUserId          (_id);
        moldObject.setUserRole        (User.UserRole.valueOf(_userRole));
        moldObject.setUserName        (_userName);
        moldObject.setUserEmail       (_userEmail);
        moldObject.setUserPassword    (_userPassword);

        return moldObject;
    }

    @Override
    public Optional<User> parse(ResultSet _resultSetThatMayContainObject) {
        Optional<User> parsedObject = Optional.empty();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            if ( res.next() ) {
                User moldObject = attachAttribute (
                                          res.getInt   ("id"),
                                          res.getString("role"),
                                          res.getString("name"),
                                          res.getString("email"),
                                          res.getString("password")
                                      );

                parsedObject = Optional.of(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _noMatchingEnumeralValue) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_noMatchingEnumeralValue);

        } catch (NullPointerException _moldObjectIsNull) {
            DatabaseExceptionExplainer.explainMoldObjectIsNull(_moldObjectIsNull);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObject;
    }

    @Override
    public ArrayList<User> parseMany(ResultSet _resultSetThatMayContainObject) {
        ArrayList<User> parsedObject = new ArrayList<>();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            while ( res.next() ) {
                User moldObject = attachAttribute (
                                          res.getInt   ("id"),
                                          res.getString("role"),
                                          res.getString("name"),
                                          res.getString("email"),
                                          res.getString("password")
                                      );

                parsedObject.add(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _noMatchingEnumeralValue) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_noMatchingEnumeralValue);

        } catch (NullPointerException _moldObjectIsNull) {
            DatabaseExceptionExplainer.explainMoldObjectIsNull(_moldObjectIsNull);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObject;
    }

    @Override
    public Object[] unparseAttributes(User _object) {
        Object [] attributes = {
                                    _object.getUserId(),
                                    _object.getUserRole().toString(),
                                    _object.getUserName(),
                                    _object.getUserEmail(),
                                    _object.getUserPassword()
                                };

        return attributes;
    }

}
