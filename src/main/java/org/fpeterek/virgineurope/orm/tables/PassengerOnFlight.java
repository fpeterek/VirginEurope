package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class PassengerOnFlight extends Table {

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

  public PassengerOnFlight() {
    super(table_name);
  }

}
