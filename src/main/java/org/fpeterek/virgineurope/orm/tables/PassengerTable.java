package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class PassengerTable extends Table {

  private static String table_name = "passenger";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("passenger_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute preferredMeal = createAttribute("preferred_meal");
  public final Attribute preferredSeat = createAttribute("preferred_seat");

  public PassengerTable() {
    super(table_name);
  }


}
