package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

import java.util.Date;

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

}
