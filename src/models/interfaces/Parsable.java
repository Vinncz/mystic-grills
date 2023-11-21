package models.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Parsable <T> {
    T parse (ResultSet _rs) throws SQLException;
    ArrayList<T> parseMultiple (ResultSet _rs) throws SQLException;
}
