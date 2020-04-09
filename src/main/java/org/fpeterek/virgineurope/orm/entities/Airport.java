package org.fpeterek.virgineurope.orm.entities;

public class Airport extends Entity {

  String icao;
  String iata;
  String name;

  public Airport(String icao, String iata, String name) {
    this.icao = icao;
    this.iata = iata;
    this.name = name;
  }

}
