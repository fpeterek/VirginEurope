package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;
import org.fpeterek.virgineurope.orm.sql.DMLQuery;
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

  public Database(String url, String user, String password) throws SQLException {
    conn = DriverManager.getConnection(url, user, password);
  }

  public QueryResult execute(Select select) throws SQLException {

    var sm = conn.createStatement();
    var res = sm.executeQuery(select.build());
    var queryResult = new QueryResult(res, select.allTables());
    res.close();
    return queryResult;

  }

  public int execute(DMLQuery query) throws SQLException {
    return conn.createStatement().executeUpdate(query.build());
  }

  public CustomQuery execute(CustomQuery query) throws SQLException {
    var rs = conn.createStatement().executeQuery(query.query());
    query.parseResult(rs);
    rs.close();
    return query;
  }

}
