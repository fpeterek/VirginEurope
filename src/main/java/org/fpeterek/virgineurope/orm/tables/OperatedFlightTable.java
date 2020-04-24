package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.OperatedFlight;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OperatedFlightTable extends Table {

  private static String table_name = "operated_flight";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
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

  public OperatedFlightTable as(String alias) {
    return (OperatedFlightTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new OperatedFlightTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public int offset() {
    return 6;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    int id = rs.getInt(offset + 1);
    var dep = rs.getTime(offset + 2);
    var arr = rs.getTime(offset + 3);
    DateTime departure = dep == null ? null : new DateTime(dep);
    DateTime arrival = arr == null ? null : new DateTime(arr);
    String flightNo = rs.getString(offset + 4);
    String aircraftReg = rs.getString(offset + 5);
    DateTime date = new DateTime(rs.getDate(offset + 6));

    return new OperatedFlight(id, departure, arrival, flightNo, null, aircraftReg, null, date,
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

  }

}
