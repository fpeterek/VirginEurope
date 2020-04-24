package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.common.TravelClass;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.FlightTicket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightTicketTable extends Table {

  private static String table_name = "flight_ticket";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute ticketId = createAttribute("ticket_id");
  public final Attribute meal = createAttribute("meal");
  public final Attribute seat = createAttribute("seat");
  public final Attribute travelClass = createAttribute("class");
  public final Attribute baggageAllowance = createAttribute("baggage_allowance");
  public final Attribute operatedId = createAttribute("operated_id");
  public final Attribute passengerId = createAttribute("passenger_id");

  public FlightTicketTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 7;
  }

  public FlightTicketTable as(String alias) {
    return (FlightTicketTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new FlightTicketTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    int id = rs.getInt(offset + 1);
    String meal = rs.getString(offset + 2);
    String seat = rs.getString(offset + 3);
    String cls = rs.getString(offset + 4);
    TravelClass travelClass = TravelClass.fromString(cls);
    int baggage = rs.getInt(offset + 5);
    int operId = rs.getInt(offset + 6);
    int paxId = rs.getInt(offset + 7);

    return new FlightTicket(id, meal, seat, travelClass, baggage, operId, null, paxId, null);
  }

}
