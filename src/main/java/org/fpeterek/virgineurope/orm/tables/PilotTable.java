package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.Pilot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PilotTable extends Table {

  private static String table_name = "pilot";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute id = createAttribute("pilot_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute certification = createAttribute("certification");
  public final Attribute isCaptain = createAttribute("is_captain");

  public PilotTable() {
    super(table_name);
  }

  public PilotTable as(String alias) {
    return (PilotTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new PilotTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public int offset() {
    return 5;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    int id = rs.getInt(offset + 1);
    String fname = rs.getString(offset + 2);
    String lname = rs.getString(offset + 3);
    String cert = rs.getString(offset + 4);
    boolean captain = rs.getBoolean(offset + 5);

    return new Pilot(id, fname, lname, cert, captain, new ArrayList<>());

  }

}
