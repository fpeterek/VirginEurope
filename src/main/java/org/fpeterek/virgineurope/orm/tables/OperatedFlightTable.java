package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatedFlightTable extends Table {

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

  public OperatedFlightTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 6;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {
    return null;
  }

}
