package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.DatabaseModificationPolicyViolatedException;
import models.User;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;
import values.strings.ValidationState;

public class UserRepository extends BaseRepository<User> {

    public UserRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("INSERT INTO %s (role, name, email, password) VALUES (?, ?, ?, ?)", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("UPDATE %s SET role = ?, name = ?, email = ?, password = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value)
        );
    }

    public static class AuthenticationReturnDatatype {
        private ValidationState state = null;
        private User associatedUser = null;

        public AuthenticationReturnDatatype () {}

        public String getMessage() {
            return this.state.value;
        }

        public ValidationState getState() {
            return state;
        }

        public void setState(ValidationState _state) {
            this.state = _state;
        }

        public User getAssociatedUser() {
            return associatedUser;
        }

        public void setAssociatedUser(User associatedUser) {
            this.associatedUser = associatedUser;
        }
    }

    public AuthenticationReturnDatatype authenticateUser (String _userEmail, String _userPassword) {;
        AuthenticationReturnDatatype returnObject = new AuthenticationReturnDatatype();

        Optional<User> target = getUserByEmail(_userEmail);
        if ( target.isEmpty() ) {
            returnObject.setState(ValidationState.UNREGISTERED_EMAIL);
            return returnObject;

        }

        if ( !(target.isPresent() && target.get().getUserPassword().equals(_userPassword)) ) {
            returnObject.setState(ValidationState.INCORRECT_PASSWORD);
            return returnObject;

        } else if (target.isPresent() && target.get().getUserPassword().equals(_userPassword)) {
            returnObject.setAssociatedUser(target.get());

        }

        return returnObject;
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

    /**
     * Provides a way to delete a concrete object from the database <br></br>
     * This variation of {@code delete} method, who is owned by UserRepository, includes
     * the logic to also delete associated Order (and by extension, Receipt and OrderItems aswel) who hold reference to said User object.
     *
     * @param _idOfAnObjectToBeDeleted • The id of which object you wish to remove
     * @return Confirmation of whether the deletion operation is successful or not.
     */
    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        try {
            OrderRepository orderRepo = new OrderRepository();
            orderRepo.deleteByUserId(_idOfAnObjectToBeDeleted);

            BaseRepository<User>.executeUpdateReturnDatatypes updateReport = executeUpdate(
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


    @Override
    protected Integer getObjectId(User _object) {
        return _object.getUserId();
    }

    @Override
    protected User setObjectId(User _object, Integer _id) {
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
                User moldObject = attachAttributeImplementation(res);

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
                User moldObject = attachAttributeImplementation(res);

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

    private User attachAttributeImplementation(ResultSet res) throws SQLException {
        User moldObject = attachAttribute (
                                            res.getInt   ("id"),
                                            res.getString("role"),
                                            res.getString("name"),
                                            res.getString("email"),
                                            res.getString("password")
                                          );
        return moldObject;
    }

    @Override
    public Object[] unparseAttributes(User _object) {
        Object [] attributes = {
                                    _object.getUserRole().toString(),
                                    _object.getUserName(),
                                    _object.getUserEmail(),
                                    _object.getUserPassword()
                                };

        return attributes;
    }

}
