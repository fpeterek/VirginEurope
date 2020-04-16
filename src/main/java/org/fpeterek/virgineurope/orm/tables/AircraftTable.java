package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.entities.Table;
import org.fpeterek.virgineurope.orm.entities.Aircraft;
import org.fpeterek.virgineurope.orm.entities.Entity;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AircraftTable extends Table {

  private static String table_name = "aircraft";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute identifier = createAttribute("identifier");
  public final Attribute engine = createAttribute("engine");
  public final Attribute economySeats = createAttribute("economy_seats");
  public final Attribute businessSeats = createAttribute("business_seats");
  public final Attribute firstSeats = createAttribute("first_seats");
  public final Attribute lastCheck = createAttribute("last_check");
  public final Attribute modelDesignator = createAttribute("aircraft_model_designator");

  public AircraftTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 7;
  }

  public AircraftTable as(String alias) {
    return (AircraftTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new AircraftTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    String id = rs.getString(offset + 1);
    String engine = rs.getString(offset + 2);
    int ecoSeats = rs.getInt(offset + 3);
    int busSeats = rs.getInt(offset + 4);
    int firstSeats = rs.getInt(offset + 5);
    DateTime lastCheck = new DateTime(rs.getDate(offset + 6));
    String designator = rs.getString(offset + 7);

    return new Aircraft(id, engine, ecoSeats, busSeats, firstSeats, lastCheck, designator, null);

  }
}
