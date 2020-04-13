package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insert extends DMLQuery {

  private static class AttributeList {

    private final List<Attribute> attrs;
    private final Insert origInsert;

    public AttributeList(List<Attribute> attributes, Insert insert) {
      attrs = attributes;
      origInsert = insert;
    }

    public Insert values(String... args) {

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

  @Override
  public String build() {
    return null;
  }
}
