package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class Aircraft extends Table {

  private static String table_name = "aircraft";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute identifier = createAttribute("identifier");
  public final Attribute engine = createAttribute("engine");
  public final Attribute economySeats = createAttribute("economy_seats");
  public final Attribute businessSeats = createAttribute("business_seats");
  public final Attribute firstSeats = createAttribute("first_seats");
  public final Attribute lastCheck = createAttribute("last_check");
  public final Attribute modelDesignator = createAttribute("aircraft_model_designator");

  public Aircraft() {
    super(table_name);
  }

}
