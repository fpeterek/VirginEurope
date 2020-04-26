package org.fpeterek.virgineurope.orm.sql;

import kotlin.Pair;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.tables.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Update extends DMLQuery {

  private final Table updateTable;
  // Java doesn't have a Pair class in it's standard library so I'm forced to use a Kotlin Pair
  private final List<Pair<String, Object>> setValues = new ArrayList<>();
  private BooleanExpr cond = null;

  private Update(Table t) {
    updateTable = t;
  }

  public static Update table(Table t) {
    return new Update(t);
  }

  public Update set(Attribute attr, Object value) {
    setValues.add(new Pair<>(attr.name, value));
    return this;
  }

  public Update where(BooleanExpr condition) {
    cond = condition;
    return this;
  }

  public Update row(Entity entity) {
    entity.formUpdate(this);
    return this;
  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var statement = connection.prepareStatement(build());
    int counter = 1;
    for (var param : setValues) {
      Util.addToStatement(counter++, statement, param.getSecond());
    }

    if (cond == null) { return statement; }
    for (Object param : cond.parameters) {
      Util.addToStatement(counter++, statement, param);
    }
    return statement;
  }

  @Override
  public String build() {

    if (setValues.isEmpty()) {
      throw new IllegalArgumentException("Not setting any values in an UPDATE query.");
    }

    var sb = new StringBuilder().append("UPDATE ").append(updateTable).append(" SET ");

    String set = setValues.stream()
            .map(pair -> pair.getFirst() + "=?")
            .collect(Collectors.joining(", "));

    sb.append(set);

    if (cond != null) {
      sb.append(" WHERE ").append(cond.toParametrizedString());
    }

    return sb.append(';').toString();
  }

  @Override
  public String toFormattedString() {

    var parametrized = build();

    for (var param : setValues) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param.getSecond()));
    }

    if (cond == null) { return parametrized; }

    for (var param : cond.parameters) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param));
    }
    return parametrized;
  }

}
