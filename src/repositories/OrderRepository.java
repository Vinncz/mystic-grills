package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.DatabaseModificationPolicyViolatedException;
import models.Order;
import models.User;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class OrderRepository extends BaseRepository<Order> {

    private UserRepository userRepo;
    private OrderItemRepository orderItemRepo;

    public OrderRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value),
            String.format("INSERT INTO %s (user_id, status, date, total) VALUES (?, ?, ?, ?)", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value),
            String.format("UPDATE %s SET user_id = ?, status = ?, date = ?, total = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value)
        );

        userRepo = new UserRepository();
        orderItemRepo = new OrderItemRepository();
    }

    public ArrayList<Order> getByUserId (Integer _userId) {
        final String query = String.format("SELECT * FROM %s WHERE user_id = ?", TABLE_NAME);
        ArrayList<Order> retrievedObject = new ArrayList<>();

        try {
            BaseRepository<Order>.executeQueryReturnDatatypes report = executeQuery(db, query, _userId);
            retrievedObject = parseMany(report.getResultSet());

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

    public ArrayList<Order> getByStatus (Order.OrderStatus _status) {
        final String query = String.format("SELECT * FROM %s WHERE status = ?", TABLE_NAME);
        ArrayList<Order> retrievedObject = new ArrayList<>();

        try {
            BaseRepository<Order>.executeQueryReturnDatatypes report = executeQuery(db, query, _status.toString());
            retrievedObject = parseMany(report.getResultSet());

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
     * Provides a way to delete a concrete object from the database. <br></br>
     * This variation of {@code delete} method, who is owned by OrderRepository, includes
     * the logic to also delete associated OrderItem and Receipt who hold reference to said Order object.
     *
     * @param _idOfAnObjectToBeDeleted • The id of which object you wish to remove
     * @return Confirmation of whether the deletion operation is successful or not. Bear in mind that this only apply to whether the deletion of the Order object is successful, NOT whether the associated Receipt or OrderItems' deletion is successful or not.
     */
    public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
        try {
            OrderItemRepository orderItemRepo = new OrderItemRepository();
            orderItemRepo.deleteByOrderId(_idOfAnObjectToBeDeleted);

            ReceiptRepository receiptRepo = new ReceiptRepository();
            receiptRepo.deleteByOrderId(_idOfAnObjectToBeDeleted);

            BaseRepository<Order>.executeUpdateReturnDatatypes updateReport = executeUpdate(
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
     * Provides a way to delete a concrete object from the database. <br></br>
     * This variation will not commit by itself, so you'll have to explicitly call {@code commit(db)} in order for changes to take effect.
     *
     * @param _idOfAnObjectToBeDeleted • The id of which object you wish to remove
     * @return Confirmation of whether the deletion operation is successful or not. Bear in mind that this only apply to whether the deletion of the Order object is successful, NOT whether the associated Receipt or OrderItems' deletion is successful or not.
     */
    public Boolean deleteWithoutCommit (Integer _idOfAnObjectToBeDeleted) {
        try {
            OrderItemRepository orderItemRepo = new OrderItemRepository();
            orderItemRepo.deleteByOrderIdWithoutCommit(_idOfAnObjectToBeDeleted);

            ReceiptRepository receiptRepo = new ReceiptRepository();
            receiptRepo.deleteByOrderIdWithoutCommit(_idOfAnObjectToBeDeleted);

            BaseRepository<Order>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                DELETE_QUERY,

                _idOfAnObjectToBeDeleted
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                return true;

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);

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
    public Boolean deleteByUserId (Integer _userId) {
        Boolean hasBeenSafeSoFar = true;

        ArrayList<Order> ordersThatHaveMatchingUserId = getByUserId(_userId);
        for ( Order o : ordersThatHaveMatchingUserId ) {
            System.out.println("ketemu order yang user_idnya: " + _userId + " yaitu order dgn id: " + o.getOrderId());
            hasBeenSafeSoFar = deleteWithoutCommit(o.getOrderId());
            if ( hasBeenSafeSoFar == false ) {
                rollback(db);
                break;
            }
        }

        if ( hasBeenSafeSoFar ) return save(db);
        return false;
    }

    @Override
    protected Integer getObjectId(Order _object) {
        return _object.getOrderId();
    }

    @Override
    protected Order setObjectId(Order _object, Integer _id) {
        _object.setOrderId(_id);
        return _object;
    }

    private Order attachAttribute (Integer _id, Integer _userId, String _status, String _date, Double _total) throws IllegalArgumentException {
        Order moldObject = new Order();

        moldObject.setOrderId       (_id);
        moldObject.setOrderItems    (orderItemRepo.getByOrderId(_id));
        moldObject.setOrderStatus   (Order.OrderStatus.valueOf(_status));
        moldObject.setOrderDate     (_date);
        moldObject.setOrderTotal    (_total);

        Optional<User> associatedUser = userRepo.getById(_userId);
        if ( associatedUser.isPresent() ) {
            moldObject.setOrderUser (associatedUser.get());

        } else {
            moldObject.setOrderUser(null);

        }

        return moldObject;
    }

    @Override
    public Optional<Order> parse(ResultSet _resultSetThatMayContainObject) {
        Optional<Order> parsedObject = Optional.empty();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            if ( res.next() ) {
                Order moldObject = attachAttribute (
                                          res.getInt    ("id"),
                                          res.getInt    ("user_id"),
                                          res.getString ("status"),
                                          res.getDate   ("date").toString(),
                                          res.getDouble ("total")
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
    public ArrayList<Order> parseMany(ResultSet _resultSetThatMayContainObject) {
        ArrayList<Order> parsedObjects = new ArrayList<>();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            while ( res.next() ) {
                Order moldObject = attachAttribute (
                                          res.getInt    ("id"),
                                          res.getInt    ("user_id"),
                                          res.getString ("status"),
                                          res.getDate   ("date").toString(),
                                          res.getDouble ("total")
                                   );

                parsedObjects.add(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _noMatchingEnumeralValue) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_noMatchingEnumeralValue);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObjects;
    }

    @Override
    public Object[] unparseAttributes(Order _object) {
        Object [] attributes = {
                                    _object.getOrderUser().getUserId(),
                                    _object.getOrderStatus().toString(),
                                    _object.getOrderDate(),
                                    _object.getOrderTotal()
                               };
        return attributes;
    }

}
