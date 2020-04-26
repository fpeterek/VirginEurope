package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.tables.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete extends DMLQuery {

  Table fromTable = null;
  BooleanExpr condition = null;

  private Delete(Table table) {
    fromTable = table;
  }

  public static Delete from(Table table) {
    return new Delete(table);
  }

  public Delete where(BooleanExpr cond) {
    condition = cond;
    return this;
  }

  public Delete row(Entity entity) {
    entity.formDelete(this);
    return this;
  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var statement = connection.prepareStatement(build());
    int counter = 1;

    if (condition == null) { return statement; }

    for (Object param : condition.parameters) {
      Util.addToStatement(counter++, statement, param);
    }
    return statement;
  }

  @Override
  public String build() {

    var sb = new StringBuilder();
    sb.append("DELETE FROM ").append(fromTable);

    if (condition != null) {
      sb.append(" WHERE ").append(condition.toParametrizedString());
    }

    return sb.append(';').toString();
  }

  @Override
  public String toFormattedString() {

    var parametrized = build();

    if (condition == null) { return parametrized; }

    for (Object param : condition.parameters) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param));
    }

    return parametrized;
  }

}
