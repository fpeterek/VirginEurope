package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

public class Aircraft extends Entity {

  String identifier;
  String engine;
  int economySeats;
  int businessSeats;
  int firstSeats;
  DateTime lastCheck;
  String modelDesignator;
  AircraftModel model;

  public Aircraft(String id, String eng, int eSeats, int bSeats, int fSeats, DateTime check, String designator,
                  AircraftModel aircraftModel) {

    identifier = id;
    engine = eng;
    economySeats = eSeats;
    businessSeats = bSeats;
    firstSeats = fSeats;
    lastCheck = check;
    modelDesignator = designator;
    model = aircraftModel;

  }

}
