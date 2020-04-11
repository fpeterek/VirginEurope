package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PilotOnFlightTable extends Table {

  private static String table_name = "pilot_on_flight";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute crew_id = createAttribute("pilot_id");
  public final Attribute flight_id = createAttribute("flight_id");

  public PilotOnFlightTable() {
    super(table_name);
  }

  public PilotOnFlightTable as(String alias) {
    return (PilotOnFlightTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new PilotOnFlightTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public int offset() {
    return 2;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {
    return null;
  }

}
