package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.tables.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Select {

  public static class JoinObject {

    private final Select select;
    private final Table table;
    private final JoinType joinType;

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

    private final String join;

    JoinType(String str) {
      join = str;
    }

    @Override
    public String toString() {
      return join;
    }
  }

  private static class Join {
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

  public List<Table> allTables() {
    List<Table> joins = joinTables();
    joins.add(0, fromTable);
    return joins;
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

  private String selectAttrs() {
    var sb = new StringBuilder();

    allTables().forEach(t ->
            t.attributes().forEach(attr -> sb.append(attr.fullName()).append(", "))
    );

    return sb.deleteCharAt(sb.length()-2).toString();
  }

  public PreparedStatement prepare(Connection connection) throws SQLException {
    var statement = connection.prepareStatement(build());
    int counter = 1;

    if (cond == null) { return statement; }

    for (Object param : cond.parameters) {
      Util.addToStatement(counter++, statement, param);
    }
    return statement;
  }

  public String build() {
    var sb = new StringBuilder();
    sb.append("SELECT ").append(selectAttrs()).append("FROM ").append(fromTable.tableName);

    for (Join join : joins) {
      var table = join.table.tableName;
      var type = join.joinType.toString();
      var cond = join.on.toString();
      sb.append(" ").append(type).append(" ").append(table);
      if (join.table.hasAlias()) {
        sb.append(" ").append(join.table.alias());
      }
      sb.append(" ON ").append(cond);
    }

    if (cond != null) {
      sb.append(" WHERE ").append(cond.toParametrizedString());
    }

    sb.append(";");

    return sb.toString();

  }

  public String toFormattedString() {

    var parametrized = build();

    if (cond == null) { return parametrized; }

    for (Object param : cond.parameters) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param));
    }

    return parametrized;
  }

  @Override
  public String toString() {
    return toFormattedString();
  }
}
