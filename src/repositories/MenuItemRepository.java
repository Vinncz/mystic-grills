package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.MenuItem;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class MenuItemRepository extends BaseRepository<MenuItem> {

    public MenuItemRepository () {
        super();
        this.TABLE_NAME = SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value;

        this.GET_BY_ID_QUERY = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
        this.GET_ALL_QUERY   = String.format("SELECT * FROM %s", TABLE_NAME);
        this.POST_QUERY      = String.format("INSERT INTO %s (name, description, price) VALUES (?, ?, ?)", TABLE_NAME);
        this.PUT_QUERY       = String.format("UPDATE %s SET name = ?, description = ?, price = ? WHERE id = ?", TABLE_NAME);
        this.DELETE_QUERY    = String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
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
    public Object[] unparseAttributes(MenuItem _object) {
        Object [] attributes = {
                                    _object.getMenuItemName(),
                                    _object.getMenuItemDescription(),
                                    _object.getMenuItemPrice()
                               };
        return attributes;

    }

    @Override
    public Integer getId (MenuItem _object) {
        return _object.getMenuItemId();
    }

    @Override
    public MenuItem setId (MenuItem _mi, Integer _id) {
        _mi.setMenuItemId(_id);
        return _mi;
    }

}
