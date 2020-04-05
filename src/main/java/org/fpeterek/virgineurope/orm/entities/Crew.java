package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.Seniority;

import java.util.List;

public class Crew {

  int id;
  String firstName;
  String lastName;
  CrewRole role;
  Seniority seniority;
  List<Flight> flights;

}
