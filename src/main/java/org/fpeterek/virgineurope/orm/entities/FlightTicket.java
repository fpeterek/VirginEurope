package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.TravelClass;

public class FlightTicket {

  String meal;
  String seat;
  TravelClass travelClass;
  int baggageAllowance;

  int operatedId;
  OperatedFlight operatedFlight;
  int passengerId;
  Passenger passenger;

}
