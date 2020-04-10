package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerOnFlightTable extends Table {

  private static String table_name = "passenger_on_flight";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute meal = createAttribute("meal");
  public final Attribute seat = createAttribute("seat");
  public final Attribute travelClass = createAttribute("travel_class");
  public final Attribute baggageAllowance = createAttribute("baggage_allowance");
  public final Attribute operatedId = createAttribute("operated_id");
  public final Attribute passengerId = createAttribute("passenger_id");

  public PassengerOnFlightTable() {
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
