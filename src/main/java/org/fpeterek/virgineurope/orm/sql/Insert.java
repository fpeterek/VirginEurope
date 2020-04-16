package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Insert extends DMLQuery {

  private static List<String> quoteVals(String[] vals) {
    return Arrays.stream(vals).map(val -> "'" + val + "'").collect(Collectors.toList());
  }

  public static class AttributeList {

    private final List<Attribute> attrs;
    private final Insert origInsert;

    AttributeList(List<Attribute> attributes, Insert insert) {
      attrs = attributes;
      origInsert = insert;
    }

    public Insert values(String... args) {

      if (args.length != attrs.size()) {
        throw new IllegalArgumentException(
          "Number of values supplied does not match number of arguments specified."
        );
      }

      origInsert.values = quoteVals(args);
      origInsert.attributes = attrs;

      return origInsert;
    }

  }

  private final Table intoTable;
  private List<Attribute> attributes;
  private List<String> values;

  private Insert(Table table) {
    intoTable = table;
  }

  public static Insert into(Table table) {
    return new Insert(table);
  }

  public AttributeList attributes(Attribute... attrs) {
    return new AttributeList(Arrays.asList(attrs), this);
  }

  public Insert values(String... args) {

    if (attributes != null && !attributes.isEmpty() && args.length != attributes.size()) {
      throw new IllegalArgumentException(
              "Number of values supplied does not match number of arguments specified."
      );
    }

    values = quoteVals(args);

    return this;
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
      sb.append(String.join(",", values));
      sb.append(")");
    }

    sb.append(";");

    return sb.toString();
  }
}
