package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.tables.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Insert extends DMLQuery {

  private static List<String> quoteVals(List<String> vals) {
    return vals.stream().map(val -> "'" + val + "'").collect(Collectors.toList());
  }

  public static class AttributeList {

    private final List<Attribute> attrs;
    private final Insert origInsert;

    AttributeList(List<Attribute> attributes, Insert insert) {
      attrs = attributes;
      origInsert = insert;
    }

    public Insert values(Object... args) {

      if (args.length != attrs.size()) {
        throw new IllegalArgumentException(
          "Number of values supplied does not match number of arguments specified."
        );
      }

      origInsert.values = Arrays.asList(args);
      origInsert.attributes = attrs;

      return origInsert;
    }

  }

  private final Table intoTable;
  private List<Attribute> attributes;
  private List<Object> values;

  private Insert(Table table) {
    intoTable = table;
  }

  public static Insert into(Table table) {
    return new Insert(table);
  }

  public Insert row(Entity entity) {
    entity.formInsert(this);
    return this;
  }

  public AttributeList attributes(Attribute... attrs) {
    return new AttributeList(Arrays.asList(attrs), this);
  }

  public Insert values(Object... args) {

    if (attributes != null && !attributes.isEmpty() && args.length != attributes.size()) {
      throw new IllegalArgumentException(
              "Number of values supplied does not match number of arguments specified."
      );
    }

    values = Arrays.asList(args);

    return this;
  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var statement = connection.prepareStatement(build());
    int counter = 1;
    for (Object param : values) {
      Util.addToStatement(counter++, statement, param);
    }
    return statement;
  }

  @Override
  public String build() {
    var sb = new StringBuilder();

    sb.append("INSERT INTO ").append(intoTable);
    if (attributes != null && !attributes.isEmpty()) {
      sb.append("(");
      sb.append(
              attributes.stream()
                      .map(attr -> attr.name)
                      .collect(Collectors.joining(","))
      );
      sb.append(")");
    }

    if (values != null && !values.isEmpty()) {
      sb.append(" VALUES ");
      sb.append("(");
      sb.append(
          values.stream().map(str -> "?").collect(Collectors.joining(","))
      );
      // sb.append(String.join(",", values));
      sb.append(")");
    }

    sb.append(";");

    return sb.toString();
  }

  @Override
  public String toFormattedString() {

    var parametrized = build();

    for (Object param : values) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param));
    }

    return parametrized;
  }
}
