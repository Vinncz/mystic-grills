package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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
            String.format("UPDATE %s SET menu_item_id = ?, quantity = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_TABLE.value)
        );

        userRepo = new UserRepository();
        orderItemRepo = new OrderItemRepository();
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
                                    _object.getOrderId(),
                                    _object.getOrderUser().getUserId(),
                                    _object.getOrderStatus(),
                                    _object.getOrderDate(),
                                    _object.getOrderTotal()
                               };
        return attributes;
    }

}
