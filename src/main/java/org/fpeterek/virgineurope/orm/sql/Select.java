package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.Table;

import java.util.AbstractMap;
import java.util.List;

public class Select {

  private enum JoinType {
    Inner("JOIN"), Left("LEFT JOIN"), Right("RIGHT JOIN"), Cartesian("CROSS JOIN");

    private String join;

    JoinType(String str) {
      join = str;
    }

    @Override
    public String toString() {
      return join;
    }
  }

  private class Join {
    public final Table table;
    public final JoinType joinType;
    public final BooleanExpr on;

    public Join(Table tb, JoinType jt, BooleanExpr onExpr) {
      table = tb;
      joinType = jt;
      on = onExpr;
    }
  }

  Table fromTable;
  List<Join> joins;
  BooleanExpr cond;

  private Select() { }

  static Select from(Table table) {
    var sel = new Select();
    sel.fromTable = table;
    return sel;
  }

  Select join(Table table, BooleanExpr on) {
    joins.add(new Join(table, JoinType.Inner, on));
    return this;
  }

  Select leftJoin(Table table, BooleanExpr on) {
    joins.add(new Join(table, JoinType.Left, on));
    return this;
  }

  Select rightJoin(Table table, BooleanExpr on) {
    joins.add(new Join(table, JoinType.Right, on));
    return this;
  }

  Select cartesianJoin(Table table, BooleanExpr on) {
    joins.add(new Join(table, JoinType.Cartesian, on));
    return this;
  }

  Select where(BooleanExpr condition) {
    cond = condition;
    return this;
  }

  public String build() {
    var sb = new StringBuilder();
    sb.append("SELECT * FROM ").append(fromTable.tableName);

    joins.forEach(join -> {
      var table = join.table.tableName;
      var type = join.joinType.toString();
      var cond = join.on.toString();
      sb.append(" ").append(type).append(" ").append(table).append(" ON ").append(cond);
    });

    if (cond != null) {
      sb.append(" WHERE ").append(cond.toString());
    }

    return sb.toString();

  }

}
