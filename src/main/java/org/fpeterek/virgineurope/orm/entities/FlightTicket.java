package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.TravelClass;

public class FlightTicket extends Entity {

  String meal;
  String seat;
  TravelClass travelClass;
  int baggageAllowance;

  int operatedId;
  OperatedFlight operatedFlight;
  int passengerId;
  Passenger passenger;

  public FlightTicket(String meal, String seat, TravelClass cls, int allowance,
                      int opId, OperatedFlight opFlight, int paxId, Passenger pax) {

    this.meal = meal;
    this.seat = seat;
    travelClass = cls;
    baggageAllowance = allowance;
    operatedId = opId;
    operatedFlight = opFlight;
    passengerId = paxId;
    passenger = pax;

  }

}
