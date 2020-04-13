package org.fpeterek.virgineurope.orm.sql;

public abstract class DMLQuery {

  public abstract String build();

  @Override
  public String toString() {
    return build();
  }
}
