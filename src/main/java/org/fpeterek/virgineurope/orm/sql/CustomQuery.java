package org.fpeterek.virgineurope.orm.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomQuery {

  public abstract CustomQuery parseResult(ResultSet rs) throws SQLException;
  public abstract PreparedStatement prepare(Connection connection) throws SQLException;
  public abstract String query();

}
