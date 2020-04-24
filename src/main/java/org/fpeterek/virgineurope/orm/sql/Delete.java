package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.tables.Table;

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
  public String build() {

    var sb = new StringBuilder();
    sb.append("DELETE FROM ").append(fromTable);

    if (condition != null) {
      sb.append(" WHERE ").append(condition);
    }

    return sb.append(';').toString();
  }

}
