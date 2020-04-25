package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.SeatType;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

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

  public String fullName() { return firstName + " " + lastName; }

  public void setFirstName(String name) { firstName = name; }
  public void setLastName(String name) { lastName = name; }
  public void setPreferredMeal(String meal) { preferredMeal = meal; }
  public void setPreferredSeat(SeatType seat) { preferredSeat = seat; }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.passenger.id.eq(String.valueOf(id)));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.passenger.firstName, firstName)
        .set(VU.passenger.lastName, lastName)
        .set(VU.passenger.preferredMeal, preferredMeal)
        .set(VU.passenger.preferredSeat, preferredSeat.dbValue())
        .where(VU.passenger.id.eq(String.valueOf(id)));
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.passenger.firstName, VU.passenger.lastName, VU.passenger.preferredMeal, VU.passenger.preferredSeat)
        .values(firstName, lastName, preferredMeal, preferredSeat.dbValue());
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
