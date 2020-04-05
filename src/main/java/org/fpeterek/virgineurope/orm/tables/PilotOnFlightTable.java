package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class PilotOnFlightTable extends Table {

  private static String table_name = "pilot_on_flight";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute crew_id = createAttribute("pilot_id");
  public final Attribute flight_id = createAttribute("flight_id");

  public PilotOnFlightTable() {
    super(table_name);
  }

}
