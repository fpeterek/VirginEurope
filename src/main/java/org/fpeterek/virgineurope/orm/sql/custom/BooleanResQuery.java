package org.fpeterek.virgineurope.orm.sql.custom;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BooleanResQuery extends CustomQuery {

  private boolean success = false;
  private final String query;
  private final String queryType;
  private final int id;

  public boolean isSuccessful() { return success; }

  protected BooleanResQuery(String queryType, String fun, int id) {
    this.queryType = queryType;
    this.id = id;
    query = "SELECT " + fun + "(?);";
  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var sm = connection.prepareStatement(query);
    sm.setInt(1, id);
    return sm;
  }

  @Override
  public CustomQuery parseResult(ResultSet rs) throws SQLException {
    rs.next();
    success = rs.getBoolean(1);
    return this;
  }

  @Override
  public String query() {
    return query;
  }

  @Override
  public String toString() {
    String status = success ? "success" : "failure";
    return queryType + "{status='" + status + "'}";
  }


}
