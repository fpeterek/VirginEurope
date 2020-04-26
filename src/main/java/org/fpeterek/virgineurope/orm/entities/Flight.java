package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;
import org.joda.time.LocalTime;


public class Flight extends Entity {

  String flightId;
  LocalTime departureTime;
  LocalTime arrivalTime;
  String aircraftModelDesignator;
  AircraftModel aircraftModel;
  int routeId;
  Route route;

  public Flight(String id, LocalTime departure, LocalTime arrival, String modelDesignator,
                AircraftModel model, int routeIdentifier, Route route) {

    flightId = id;
    departureTime = departure;
    arrivalTime = arrival;
    aircraftModelDesignator = modelDesignator;
    aircraftModel = model;
    routeId = routeIdentifier;
    this.route = route;

  }

  public String getFlightId() { return flightId; }
  public LocalTime getDepartureTime() { return departureTime; }
  public LocalTime getArrivalTime() { return arrivalTime; }
  public String getModelDesignator() { return aircraftModelDesignator; }
  public AircraftModel getAircraftModel() { return aircraftModel; }
  public int getRouteId() { return routeId; }
  public Route getRoute() { return route; }

  public void setDepartureTime(LocalTime time) { departureTime = time; }
  public void setArrivalTime(LocalTime time) { arrivalTime = time; }
  public void setModelDesignator(String designator) { aircraftModelDesignator = designator; }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.flight.id.eq(flightId));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.flight.routeId, routeId)
        .set(VU.flight.aircraftModelDesignator, aircraftModelDesignator)
        .set(VU.flight.arrivalTime, arrivalTime)
        .set(VU.flight.departureTime, departureTime)
        .where(VU.flight.id.eq(flightId));

  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.flight.id, VU.flight.routeId, VU.flight.aircraftModelDesignator, VU.flight.arrivalTime,
        VU.flight.departureTime)
        .values(flightId, routeId, aircraftModelDesignator, arrivalTime, departureTime);
  }

  @Override
  public void add(Entity entity) {
    if (entity instanceof Route) {
      route = (Route)entity;
    } else if (entity instanceof AircraftModel) {
      aircraftModel = (AircraftModel)entity;
    }
  }

  @Override
  public String toString() {
    return "Flight{" +
            "flightId='" + flightId + '\'' +
            ", departureTime=" + departureTime +
            ", arrivalTime=" + arrivalTime +
            ", aircraftModelDesignator='" + aircraftModelDesignator + '\'' +
            ", aircraftModel=" + aircraftModel +
            ", routeId=" + routeId +
            ", route=" + route +
            '}';
  }
}
