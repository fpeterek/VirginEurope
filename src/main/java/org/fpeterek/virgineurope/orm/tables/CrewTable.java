package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;

public class CrewTable extends Table {

  private static String table_name = "crew";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute id = createAttribute("crew_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute role = createAttribute("role");
  public final Attribute seniority = createAttribute("seniority");

  public CrewTable() {
    super(table_name);
  }

}
