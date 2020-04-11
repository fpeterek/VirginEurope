package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.common.EtopsCertified;
import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Aircraft;
import org.fpeterek.virgineurope.orm.entities.AircraftModel;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AircraftModelTable extends Table {

  private static String table_name = "aircraft_model";

  private Attribute createAttribute(String attr) {
    return createAttribute(attr, table_name);
  }

  public final Attribute designator = createAttribute("designator");
  public final Attribute manufacturer = createAttribute("manufacturer");
  public final Attribute family = createAttribute("family");
  public final Attribute fullType = createAttribute("full_type");
  public final Attribute etopsCertified = createAttribute("etops_certified");
  public final Attribute etopsRating = createAttribute("etops_rating");
  public final Attribute rangeNmi = createAttribute("range_nmi");
  public final Attribute mtow = createAttribute("mtow");

  public AircraftModelTable() {
    super(table_name);
  }

  public AircraftModelTable as(String alias) {
    return (AircraftModelTable) super.as(alias);
  }

  @Override
  protected Table getAliasedTable(String alias) {
    var table = new AircraftModelTable();
    table.nameAlias = alias;
    return table;
  }

  @Override
  public int offset() {
    return 8;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    String designator = rs.getString(1 + offset);
    String manufacturer = rs.getString(2 + offset);
    String family = rs.getString(3 + offset);
    String fullType = rs.getString(4 + offset);
    EtopsCertified certified = EtopsCertified.fromString(rs.getString(5 + offset));
    int etopsRating = rs.getInt(6 + offset);
    int range = rs.getInt(7 + offset);
    int mtow = rs.getInt(8 + offset);

    return new AircraftModel(designator, manufacturer, family, fullType, certified,
            etopsRating, range, mtow);

  }
}
