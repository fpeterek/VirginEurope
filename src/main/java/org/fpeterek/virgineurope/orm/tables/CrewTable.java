package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Crew;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrewTable extends Table {

  private static String table_name = "crew";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute id = createAttribute("crew_id");
  public final Attribute firstName = createAttribute("first_name");
  public final Attribute lastName = createAttribute("last_name");
  public final Attribute role = createAttribute("role");
  public final Attribute seniority = createAttribute("seniority");

  public CrewTable() {
    super(table_name);
  }

  public CrewTable as(String alias) {
    return (CrewTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new CrewTable();
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
    CrewRole role = CrewRole.fromString(rs.getString(offset + 4));
    Seniority seniority = Seniority.fromString(rs.getString(offset + 5));

    return new Crew(id, fname, lname, role, seniority, new ArrayList<>());

  }

}
