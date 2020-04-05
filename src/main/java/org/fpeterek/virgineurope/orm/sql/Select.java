package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.Table;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Select {

  public class JoinObject {

    private Select select;
    private Table table;
    private JoinType joinType;

    protected JoinObject(Select sel, Table t, JoinType type) {
      select = sel;
      table = t;
      joinType = type;
    }

    public Select on(BooleanExpr onCond) {
      select.addJoin(new Join(table, joinType, onCond));
      return select;
    }

  }

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
  List<Join> joins = new ArrayList<>();
  BooleanExpr cond;

  public List<Table> joinTables() {
    return joins.stream().map(join -> join.table).collect(Collectors.toList());
  }

  private Select() { }

  public static Select from(Table table) {
    var sel = new Select();
    sel.fromTable = table;
    return sel;
  }

  private void addJoin(Join j) {
    joins.add(j);
  }

  public JoinObject join(Table table) {
    return new JoinObject(this, table, JoinType.Inner);
  }

  public JoinObject leftJoin(Table table) {
    return new JoinObject(this, table, JoinType.Left);
  }

  public JoinObject rightJoin(Table table) {
    return new JoinObject(this, table, JoinType.Right);
  }

  public JoinObject cartesianJoin(Table table) {
    return new JoinObject(this, table, JoinType.Cartesian);
  }

  public Select where(BooleanExpr condition) {
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

    sb.append(";");

    return sb.toString();

  }

  @Override
  public String toString() {
    return build();
  }
}
