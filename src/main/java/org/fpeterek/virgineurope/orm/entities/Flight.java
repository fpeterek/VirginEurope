package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

public class Flight {
  String flightId;
  DateTime departureTime;
  DateTime arrivalTime;
  String aircraftModelDesignator;
  AircraftModel aircraftModel;
  int routeId;
  Route route;
}
