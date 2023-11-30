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
        super (
            SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value),
            String.format("INSERT INTO %s (name, description, price) VALUES (?, ?, ?)", SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value),
            String.format("UPDATE %s SET name = ?, description = ?, price = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_MENU_ITEM_TABLE.value)
        );
    }

    @Override
    public Integer getObjectId (MenuItem _object) {
        return _object.getMenuItemId();
    }

    @Override
    public MenuItem setObjectId (MenuItem _object, Integer _id) {
        _object.setMenuItemId(_id);
        return _object;
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
            if ( res.next() ) {
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

}
