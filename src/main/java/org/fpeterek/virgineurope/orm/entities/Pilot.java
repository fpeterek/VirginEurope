package org.fpeterek.virgineurope.orm.entities;

import java.util.List;

public class Pilot extends Entity {

  int id;
  String firstName;
  String lastName;
  String certification;
  boolean isCaptain;

  List<Flight> flights;

  public Pilot(int pilotId, String fname, String lname, String cert, boolean captain,
               List<Flight> flights) {

    id = pilotId;
    firstName = fname;
    lastName = lname;
    certification = cert;
    isCaptain = captain;
    this.flights = flights;

  }

}
