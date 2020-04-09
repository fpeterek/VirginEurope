package org.fpeterek.virgineurope.orm.entities;

public class Aircraft extends Entity {

  String identifier;
  String engine;
  int economySeats;
  int businessSeats;

  public Aircraft(String id, String eng, int eSeats, int bSeats) {
    identifier = id;
    engine = eng;
    economySeats = eSeats;
    businessSeats = bSeats;
  }

}
