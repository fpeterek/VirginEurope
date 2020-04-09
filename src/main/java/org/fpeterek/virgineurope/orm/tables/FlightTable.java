package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class FlightTable extends Table {

  private static String table_name = "flight";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("flight_id");
  public final Attribute departureTime = createAttribute("departure_time");
  public final Attribute arrivalTime = createAttribute("arrival_time");
  public final Attribute aircraftModelDesignator = createAttribute("aircraft_model_designator");
  public final Attribute routeId = createAttribute("route_id");

  public FlightTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 5;
  }

}
