package org.fpeterek.virgineurope.orm.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DMLQuery {

  public abstract PreparedStatement prepare(Connection connection) throws SQLException;
  public abstract String build();
  public abstract String toFormattedString();

  @Override
  public String toString() {
    return toFormattedString();
  }

}
