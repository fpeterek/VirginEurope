package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CrewOnFlightTable extends Table {

  private static String table_name = "crew_on_flight";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute crew_id = createAttribute("crew_id");
  public final Attribute flight_id = createAttribute("flight_id");

  public CrewOnFlightTable() {
    super(table_name);
  }

  public CrewOnFlightTable as(String alias) {
    return (CrewOnFlightTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new CrewOnFlightTable();
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
