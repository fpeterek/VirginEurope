package org.fpeterek.virgineurope.orm.sql;

import kotlin.Pair;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.entities.Table;

import java.util.*;
import java.util.stream.Collectors;

public class Update extends DMLQuery {

  private final Table updateTable;
  // Java doesn't have a Pair class in it's standard library so I'm forced to use a Kotlin Pair
  private List<Pair<String, String>> setValues = new ArrayList<>();
  private BooleanExpr cond = null;

  private Update(Table t) {
    updateTable = t;
  }

  public static Update table(Table t) {
    return new Update(t);
  }

  public Update set(Attribute attr, String value) {
    setValues.add(new Pair<>(attr.name, "'" + value + "'"));
    return this;
  }

  public Update where(BooleanExpr condition) {
    cond = condition;
    return this;
  }

  @Override
  public String build() {

    if (setValues.isEmpty()) {
      throw new IllegalArgumentException("Not setting any values in an UPDATE query.");
    }

    var sb = new StringBuilder().append("UPDATE ").append(updateTable).append(" SET ");

    String set = setValues.stream()
            .map(pair -> pair.getFirst() + "=" + pair.getSecond())
            .collect(Collectors.joining(", "));

    sb.append(set);

    if (cond != null) {
      sb.append(" WHERE ").append(cond);
    }

    return sb.append(';').toString();
  }

}
