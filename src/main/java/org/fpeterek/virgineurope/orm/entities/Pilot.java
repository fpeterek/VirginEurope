package org.fpeterek.virgineurope.orm.entities;

import java.util.ArrayList;
import java.util.List;

public class Pilot extends Entity {

  int id;
  String firstName;
  String lastName;
  String certification;
  boolean captain;

  List<Flight> flights;

  public Pilot(int pilotId, String fname, String lname, String cert, boolean captain,
               List<Flight> flights) {

    id = pilotId;
    firstName = fname;
    lastName = lname;
    certification = cert;
    this.captain = captain;
    this.flights = flights;

  }

  public int getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getCertification() { return certification; }
  public boolean isCaptain() { return captain; }
  public List<Flight> getFlights() { return flights; }

  @Override
  public void add(Entity entity) {
    if (!(entity instanceof Flight)) {
      return;
    }
    if (flights == null) {
      flights = new ArrayList<>();
    }
    flights.add((Flight)entity);
  }

  @Override
  public String toString() {
    return "Pilot{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", certification='" + certification + '\'' +
            ", isCaptain=" + captain +
            ", flights=" + flights +
            '}';
  }
}
