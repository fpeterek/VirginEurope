package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.SeatType;

import java.util.List;

public class Passenger {

  int id;
  String firstName;
  String lastName;
  String preferredMeal;
  SeatType preferredSeat;

  List<FlightTicket> tickets;

}
