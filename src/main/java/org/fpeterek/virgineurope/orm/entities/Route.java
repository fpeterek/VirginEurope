package org.fpeterek.virgineurope.orm.entities;

public class Route extends Entity {
  int id;
  int distance;
  int etopsRequirement;
  String originIcao;
  Airport origin;
  String destinationIcao;
  Airport destination;

  public Route(int routeId, int dist, int etopsReq, String origIcao, Airport orig,
               String destIcao, Airport dest) {

    id = routeId;
    distance = dist;
    etopsRequirement = etopsReq;
    originIcao = origIcao;
    origin = orig;
    destinationIcao = destIcao;
    destination = dest;

  }

}
