package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.common.SeatType;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerTable extends Table {

  private static String table_name = "passenger";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute id = createAttribute("passenger_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute preferredMeal = createAttribute("preferred_meal");
  public final Attribute preferredSeat = createAttribute("preferred_seat");

  public PassengerTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 5;
  }

  public PassengerTable as(String alias) {
    return (PassengerTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new PassengerTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    int id = rs.getInt(offset + 1);
    String fname = rs.getString(offset + 2);
    String lname = rs.getString(offset + 3);
    String prefMeal = rs.getString(offset + 4);
    SeatType prefSeat = SeatType.fromString(rs.getString(offset + 5));

    return new Passenger(id, fname, lname, prefMeal, prefSeat, new ArrayList<>());
  }

}
