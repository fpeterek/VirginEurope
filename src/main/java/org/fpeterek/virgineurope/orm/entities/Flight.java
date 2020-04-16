package org.fpeterek.virgineurope.orm.entities;

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
