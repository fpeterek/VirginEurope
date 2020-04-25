package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

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

  public void setFirstName(String name) { firstName = name; }
  public void setLastName(String name) { lastName = name; }
  public void setRole(CrewRole crewRole) { role = crewRole; }
  public void setSeniority(Seniority sen) { seniority = sen; }


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
  public void formDelete(Delete query) {
    query.where(VU.crew.id.eq(String.valueOf(id)));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.crew.firstName, firstName)
        .set(VU.crew.lastName, lastName)
        .set(VU.crew.role, role.dbValue())
        .set(VU.crew.seniority, seniority.dbValue())
        .where(VU.crew.id.eq(String.valueOf(id)));

  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.crew.firstName, VU.crew.lastName, VU.crew.role, VU.crew.seniority)
        .values(firstName, lastName, role.dbValue(), seniority.dbValue());
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
