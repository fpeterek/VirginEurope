package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;
import org.joda.time.DateTime;


public class Flight extends Entity {

  String flightId;
  DateTime departureTime;
  DateTime arrivalTime;
  String aircraftModelDesignator;
  AircraftModel aircraftModel;
  int routeId;
  Route route;

  public Flight(String id, DateTime departure, DateTime arrival, String modelDesignator,
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
  public DateTime getDepartureTime() { return departureTime; }
  public DateTime getArrivalTime() { return arrivalTime; }
  public String getModelDesignator() { return aircraftModelDesignator; }
  public AircraftModel getAircraftModel() { return aircraftModel; }
  public int getRouteId() { return routeId; }
  public Route getRoute() { return route; }

  public void setDepartureTime(DateTime time) { departureTime = time; }
  public void setArrivalTime(DateTime time) { arrivalTime = time; }
  public void setModelDesignator(String designator) { aircraftModelDesignator = designator; }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.flight.id.eq(String.valueOf(flightId)));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.flight.routeId, String.valueOf(routeId))
        .set(VU.flight.aircraftModelDesignator, aircraftModelDesignator)
        .set(VU.flight.arrivalTime, arrivalTime.toString("HH:mm:ss"))
        .set(VU.flight.departureTime, departureTime.toString("HH:mm:ss"))
        .where(VU.flight.id.eq(flightId));

  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.flight.id, VU.flight.routeId, VU.flight.aircraftModelDesignator, VU.flight.arrivalTime,
        VU.flight.departureTime)
        .values(flightId, String.valueOf(routeId), aircraftModelDesignator, arrivalTime.toString("HH:mm:ss"),
            departureTime.toString("HH:mm:ss"));
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
