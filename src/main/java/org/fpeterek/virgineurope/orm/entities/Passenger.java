package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.SeatType;

import java.util.ArrayList;
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

  public int getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getPreferredMeal() { return preferredMeal; }
  public SeatType getPreferredSeat() { return preferredSeat; }
  public List<FlightTicket> getFlightTickets() { return tickets; }

  public String fullName() {
    return firstName + " " + lastName;
  }

  @Override
  public void add(Entity entity) {
    if (!(entity instanceof FlightTicket)) {
      return;
    }
    if (tickets == null) {
      tickets = new ArrayList<>();
    }
    tickets.add((FlightTicket)entity);
  }

  @Override
  public String toString() {
    return "Passenger{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", preferredMeal='" + preferredMeal + '\'' +
            ", preferredSeat=" + preferredSeat +
            ", tickets=" + tickets +
            '}';
  }
}
