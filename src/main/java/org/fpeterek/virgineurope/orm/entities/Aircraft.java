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

  public String getIdentifier() { return identifier; }
  public String getEngine() { return engine; }
  public int getEconomySeats() { return economySeats; }
  public int getBusinessSeats() { return businessSeats; }
  public int getFirstSeats() { return firstSeats; }
  public DateTime getLastCheck() { return lastCheck; }
  public String getModelDesignator() { return modelDesignator; }

  @Override
  public void add(Entity entity) {
    if (entity instanceof AircraftModel) {
      model = (AircraftModel) entity;
    }
  }

  @Override
  public String toString() {
    return "Aircraft{" +
            "identifier='" + identifier + '\'' +
            ", engine='" + engine + '\'' +
            ", economySeats=" + economySeats +
            ", businessSeats=" + businessSeats +
            ", firstSeats=" + firstSeats +
            ", lastCheck=" + lastCheck +
            ", modelDesignator='" + modelDesignator + '\'' +
            ", model=" + model +
            '}';
  }
}
