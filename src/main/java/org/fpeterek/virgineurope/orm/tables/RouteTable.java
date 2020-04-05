package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class RouteTable extends Table {

  private static String table_name = "pilot_on_flight";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("route_id");
  public final Attribute distance = createAttribute("distance");
  public final Attribute etopsRequirement = createAttribute("etops_requirement");
  public final Attribute origin = createAttribute("origin");
  public final Attribute destination = createAttribute("destination");

  public RouteTable() {
    super(table_name);
  }

}
