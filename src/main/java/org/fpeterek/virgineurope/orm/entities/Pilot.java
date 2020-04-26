package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

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

  public void setFirstName(String name) { firstName = name; }
  public void setLastName(String name) { lastName = name; }
  public void setCertification(String cert) { certification = cert; }
  public void setCaptain(boolean isCaptain) { captain = isCaptain; }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.pilot.id.eq(id));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.pilot.firstName, firstName)
        .set(VU.pilot.lastName, lastName)
        .set(VU.pilot.certification, certification)
        .set(VU.pilot.isCaptain, captain)
        .where(VU.pilot.id.eq(id));
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.pilot.firstName, VU.pilot.lastName, VU.pilot.certification, VU.pilot.isCaptain)
        .values(firstName, lastName, certification, captain);
  }

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
