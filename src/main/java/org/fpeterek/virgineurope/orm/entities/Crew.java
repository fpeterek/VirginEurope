package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;

import java.util.ArrayList;
import java.util.List;

public class Crew extends Entity {

  int id;
  String firstName;
  String lastName;
  CrewRole role;
  Seniority seniority;
  List<Flight> flights;

  public Crew(int identifier, String fName, String lName, CrewRole crewRole, Seniority sen,
              List<Flight> crewFlights) {
    id = identifier;
    firstName = fName;
    lastName = lName;
    role = crewRole;
    seniority = sen;
    flights = crewFlights;
  }

  public int getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public CrewRole getRole() { return role; }
  public Seniority getSeniority() { return seniority; }
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
    return "Crew{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", role=" + role +
            ", seniority=" + seniority +
            ", flights=" + flights +
            '}';
  }
}
