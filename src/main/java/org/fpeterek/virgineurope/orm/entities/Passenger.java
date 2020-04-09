package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.SeatType;

import java.util.List;

public class Passenger extends Entity {

  int id;
  String firstName;
  String lastName;
  String preferredMeal;
  SeatType preferredSeat;

  List<FlightTicket> tickets;

  public Passenger(int paxId, String fname, String lname, String prefMeal, SeatType prefSeat,
                   List<FlightTicket> flightTickets) {

    id = paxId;
    firstName = fname;
    lastName = lname;
    preferredMeal = prefMeal;
    preferredSeat = prefSeat;
    tickets = flightTickets;

  }

}
