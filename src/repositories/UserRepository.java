package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.User;
import models.User.UserRole;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class UserRepository extends BaseRepository<User> {

    public UserRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?"                               , SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("SELECT * FROM %s"                                            , SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("INSERT INTO %s (menu_item_id, quantity) VALUES (?, ?)"       , SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("UPDATE %s SET menu_item_id = ?, quantity = ? WHERE id = ?"   , SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?"                                 , SYSTEM_PROPERTIES.DATABASE_USER_TABLE.value)
        );
    }

    public User authenticateUser (String _userEmail, String _userPassword) {;
        User target = getUserByEmail(_userEmail);

        if (target != null && target.getUserPassword().equals(_userPassword)) {
            return target;
        }

        return null;
    }

    public User getUserByEmail(String _userEmail) {

        final String QUERY = String.format("SELECT * FROM %s WHERE email = ?", TABLE_NAME);

        User target = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(QUERY);

            ps.setString(1, _userEmail);

            ResultSet res = ps.executeQuery();

            if (res.next()) {
                target = new User();
                User.UserRole role = UserRole.valueOf(res.getString("role"));

                target.setUserId       ( res.getInt("id"));
                target.setUserName     (res.getString("name"));
                target.setUserEmail    (res.getString("email"));
                target.setUserPassword (res.getString("password"));
                target.setUserRole     (role);

            }

            res.close();
            ps.close();

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _enumeralAssignmentFailure) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_enumeralAssignmentFailure);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

         return target;
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

    private User attachAttribute (Integer _id, String _userRole, String _userName, String _userEmail, String _userPassword) {
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
            if ( res.next() ) {
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
