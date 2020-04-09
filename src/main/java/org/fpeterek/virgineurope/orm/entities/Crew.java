package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;

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

}
