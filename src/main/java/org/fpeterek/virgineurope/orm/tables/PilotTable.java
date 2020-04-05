package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class PilotTable extends Table {

  private static String table_name = "pilot";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("pilot_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute certification = createAttribute("certification");
  public final Attribute isCaptain = createAttribute("is_captain");

  public PilotTable() {
    super(table_name);
  }

}
