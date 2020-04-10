package org.fpeterek.virgineurope.orm.tables;

import org.fpeterek.virgineurope.orm.Attribute;
import org.fpeterek.virgineurope.orm.Table;
import org.fpeterek.virgineurope.orm.entities.Airport;
import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportTable extends Table {

  private static String table_name = "airport";

  private static Attribute createAttribute(String attr) {
    return new Attribute(attr, table_name);
  }

  public final Attribute icao = createAttribute("icao");
  public final Attribute iata = createAttribute("iata");
  public final Attribute name = createAttribute("name");

  public AirportTable() {
    super(table_name);
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public Entity parseFrom(ResultSet rs, int offset) throws SQLException {

    String icao = rs.getString(offset + 1);
    String iata = rs.getString(offset + 2);
    String name = rs.getString(offset + 3);

    return new Airport(icao, iata, name);

  }

}
