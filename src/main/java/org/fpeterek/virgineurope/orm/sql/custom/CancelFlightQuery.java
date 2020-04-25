package org.fpeterek.virgineurope.orm.sql.custom;

public class CancelFlightQuery extends BooleanResQuery {
  public CancelFlightQuery(int id) {
    super("CancelFlightQuery", "CancelFlight", id);
  }
}
