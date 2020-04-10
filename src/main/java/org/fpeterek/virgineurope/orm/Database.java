package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.sql.Select;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

  Connection conn;

  public Database() throws SQLException {
    conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/pet0342",
            "fpeterek",
            ""
    );
  }

  public QueryResult execute(Select select) throws SQLException {

    var sm = conn.createStatement();
    var res = sm.executeQuery(select.build());
    return new QueryResult(res, select.allTables());

  }

  /*public <T extends Entity> List<T> select(Class<T> _class) throws SQLException {
    var sm = conn.createStatement();
    var res = sm.executeQuery("select * from " + _class.getSimpleName());

    try {
      List<T> list = (List<T>) _class.getMethod("listFromQuery", ResultSet.class).invoke(null, res);
      res.close();
      return list;
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      return null;
    }
  }*/

  /*public <T extends Entity> List<T> select(Class<T> _class) throws SQLException {
    var sm = conn.createStatement();
    var res = sm.executeQuery("select * from " + _class.getSimpleName());
    var list = T.listFromQuery(res);
    System.out.println("List equals null: " + (list == null));
    return T.listFromQuery(res);
  }*/

}
