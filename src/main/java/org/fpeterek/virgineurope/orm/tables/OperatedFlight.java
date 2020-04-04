package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class OperatedFlight extends Table {

  private static String table_name = "operated_flight";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("operated_id");
  public final Attribute actualDeparture = createAttribute("actual_departure");
  public final Attribute actualArrival = createAttribute("actual_arrival");
  public final Attribute flightId = createAttribute("flight_id");
  public final Attribute aircraftIdentifier = createAttribute("aircraft_identifier");
  public final Attribute date = createAttribute("date");

  public OperatedFlight() {
    super(table_name);
  }

}
