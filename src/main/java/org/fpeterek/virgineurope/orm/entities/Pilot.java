package org.fpeterek.virgineurope.orm.entities;

import java.util.List;

public class Pilot {

  int id;
  String firstName;
  String lastName;
  String certification;
  boolean isCaptain;

  List<Flight> flights;

}
