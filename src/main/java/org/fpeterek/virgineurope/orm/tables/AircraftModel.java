package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class AircraftModel extends Table {

  private static String table_name = "aircraft_mode";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute designator = createAttribute("designator");
  public final Attribute manufacturer = createAttribute("manufacturer");
  public final Attribute family = createAttribute("family");
  public final Attribute fullType = createAttribute("full_type");
  public final Attribute etopsCertified = createAttribute("etops_certified");
  public final Attribute etopsRating = createAttribute("etops_rating");
  public final Attribute rangeNmi = createAttribute("range_nmi");
  public final Attribute mtow = createAttribute("mtow");

  public AircraftModel() {
    super(table_name);
  }

}
