package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

public class Airport extends Entity {

  String icao;
  String iata;
  String name;

  public Airport(String icao, String iata, String name) {
    this.icao = icao;
    this.iata = iata;
    this.name = name;
  }

  public String getIcao() { return icao; }
  public String getIata() { return iata; }
  public String getName() { return name; }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.airport.icao.eq(icao));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.airport.name, name)
        .set(VU.airport.iata, iata)
        .where(VU.airport.icao.eq(icao));
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.airport.icao, VU.airport.iata, VU.airport.name)
            .values(icao, iata, name);
  }

  @Override
  public String toString() {
    return "Airport{" +
            "icao='" + icao + '\'' +
            ", iata='" + iata + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
