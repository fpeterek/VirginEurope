package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.fpeterek.virgineurope.orm.entities.Route;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteTable extends Table {

  private static String table_name = "route";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute id = createAttribute("route_id");
  public final Attribute distance = createAttribute("distance");
  public final Attribute etopsRequirement = createAttribute("etops_requirement");
  public final Attribute origin = createAttribute("origin");
  public final Attribute destination = createAttribute("destination");

  public RouteTable() {
    super(table_name);
  }

  public RouteTable as(String alias) {
    return (RouteTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new RouteTable();
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
    int distance = rs.getInt(offset + 2);
    int etopsReq = rs.getInt(offset + 3);
    String orig = rs.getString(offset + 4);
    String dest = rs.getString(offset + 5);

    return new Route(id, distance, etopsReq, orig, null, dest, null);

  }

}
