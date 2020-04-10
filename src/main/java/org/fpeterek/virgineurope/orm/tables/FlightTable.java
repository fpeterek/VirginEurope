package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.Flight;
import org.fpeterek.virgineurope.orm.entities.Route;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;

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

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    String id = rs.getString(offset + 1);
    DateTime departureTime = new DateTime(rs.getTime(offset + 2));
    DateTime arrivalTime = new DateTime(rs.getTime(offset + 3));
    String modelDes = rs.getString(offset + 4);
    int routeId = rs.getInt(offset + 5);

    return new Flight(id, departureTime, arrivalTime, modelDes, null, routeId, null);

  }

}
