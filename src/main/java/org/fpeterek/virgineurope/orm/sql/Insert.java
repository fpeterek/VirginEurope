package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.orm.Table;

public class Insert extends DMLQuery {

  private final Table intoTable;

  private Insert(Table table) {
    intoTable = table;
  }

  public static Insert into(Table table) {
    return new Insert(table);
  }

  @Override
  public String build() {
    return null;
  }
}
