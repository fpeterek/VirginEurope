package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Table {

  public final String tableName;

  protected Table(String table) {
    tableName = table;
  }

  public abstract int offset();
  public abstract Entity parseFrom(ResultSet rs, int offset) throws SQLException;

}
