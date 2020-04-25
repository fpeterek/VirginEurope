package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;
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

  @Override
  public void formDelete(Delete query) {
    query.where(VU.aircraft.identifier.eq(identifier));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.aircraft.engine, engine)
        .set(VU.aircraft.lastCheck, lastCheck.toString("YYYY-MM-DD"))
        .set(VU.aircraft.firstSeats, String.valueOf(firstSeats))
        .set(VU.aircraft.businessSeats, String.valueOf(businessSeats))
        .set(VU.aircraft.modelDesignator, modelDesignator)
        .set(VU.aircraft.economySeats, String.valueOf(economySeats))
        .where(VU.aircraft.identifier.eq(identifier));

  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.aircraft.identifier, VU.aircraft.engine, VU.aircraft.lastCheck,
            VU.aircraft.modelDesignator, VU.aircraft.economySeats, VU.aircraft.businessSeats,
            VU.aircraft.firstSeats)
          .values(identifier, engine, lastCheck.toString("YYYY-MM-DD"),
                  modelDesignator, String.valueOf(economySeats), String.valueOf(businessSeats),
                  String.valueOf(firstSeats));
  }

  private static void checkSeatCount(int count) {
    if (count < 0) {
      throw new IllegalArgumentException("Number of seats cannot be less than zero. ");
    }
  }

  public String getIdentifier() { return identifier; }
  public String getEngine() { return engine; }
  public int getEconomySeats() { return economySeats; }
  public int getBusinessSeats() { return businessSeats; }
  public int getFirstSeats() { return firstSeats; }
  public DateTime getLastCheck() { return lastCheck; }
  public String getModelDesignator() { return modelDesignator; }

  public void setEconomySeats(int newVal) {
    checkSeatCount(newVal);
    economySeats = newVal;
  }

  public void setBusinessSeats(int newVal) {
    checkSeatCount(newVal);
    businessSeats = newVal;
  }

  public void setFirstSeats(int newVal) {
    checkSeatCount(newVal);
    firstSeats = newVal;
  }

  public void setLastCheck(DateTime date) { lastCheck = date; }

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
