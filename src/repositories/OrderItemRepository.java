package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.MenuItem;
import models.OrderItem;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class OrderItemRepository extends BaseRepository<OrderItem> {

    private MenuItemRepository menuItemRepo = null;

    public OrderItemRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value),
            String.format("INSERT INTO %s (order_id, menu_item_id, quantity) VALUES (?, ?, ?)", SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value),
            String.format("UPDATE %s SET order_id = ?, menu_item_id = ?, quantity = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_ORDER_ITEM_TABLE.value)
        );

        menuItemRepo = new MenuItemRepository();
    }

    public ArrayList<OrderItem> getByOrderId (Integer _orderId) {
        ArrayList<OrderItem> retrievedObjects = new ArrayList<>();
        final String query = String.format("SELECT * FROM %s WHERE order_id = ?", TABLE_NAME);

        try {
            BaseRepository<OrderItem>.executeQueryReturnDatatypes report = executeQuery(db, query, _orderId);
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
    protected Integer getObjectId (OrderItem _object) {
        return _object.getOrderItemId();
    }

    @Override
    protected OrderItem setObjectId (OrderItem _object, Integer _id) {
        _object.setOrderItemId(_id);
        return _object;
    }

    private OrderItem attachAttribute (Integer _id, Integer _orderId, Integer _menuItemId, Integer _quantity) {
        OrderItem moldObject = new OrderItem();

        moldObject.setOrderItemId         (_id);
        moldObject.setOrderId             (_orderId);
        moldObject.setQuantity            (_quantity);

        Optional<MenuItem> mi = menuItemRepo.getById(_menuItemId);
        if (menuItemRepo.getById(_menuItemId).isPresent()) {
            moldObject.setMenuItem(mi.get());

        } else {
            moldObject.setMenuItem(null);

        }

        return moldObject;
    }

    @Override
    public Optional<OrderItem> parse (ResultSet _resultSetThatMayContainObject) {
        Optional<OrderItem> parsedObject = Optional.empty();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            if ( res.next() ) {
                OrderItem moldObject = attachAttribute(
                                            res.getInt("id"),
                                            res.getInt("order_id"),
                                            res.getInt("menu_item_id"),
                                            res.getInt("quantity")
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
    public ArrayList<OrderItem> parseMany (ResultSet _resultSetThatMayContainObject) {
        ArrayList<OrderItem> parsedObject = new ArrayList<>();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            while ( res.next() ) {
                OrderItem moldObject = attachAttribute(
                                            res.getInt("id"),
                                            res.getInt("order_id"),
                                            res.getInt("menu_item_id"),
                                            res.getInt("quantity")
                                       );

                parsedObject.add(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObject;
    }

    @Override
    public Object[] unparseAttributes (OrderItem _object) {
        Object [] attributes = {
                                    _object.getOrderId(),
                                    _object.getMenuItem().getMenuItemId(),
                                    _object.getQuantity()
                               };

        return attributes;
    }

}
