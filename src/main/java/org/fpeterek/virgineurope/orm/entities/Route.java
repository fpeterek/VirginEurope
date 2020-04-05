package org.fpeterek.virgineurope.orm.entities;

public class Route extends Entity {
  int id;
  int distance;
  int etopsRequirement;
  String originIcao;
  Airport origin;
  String destinationIcao;
  Airport destination;
}
