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

  public int getId() { return id; }
  public int getDistance() { return distance; }
  public int getEtopsRequirement() { return etopsRequirement; }
  public String getOriginIcao() { return originIcao; }
  public Airport getOrigin() { return origin; }
  public String getDestinationIcao() { return destinationIcao; }
  public Airport getDestination() { return destination; }

  @Override
  public void add(Entity entity) {
    if (entity instanceof Airport) {
      var airport = (Airport)entity;

      if (airport.icao.equals(originIcao)) {
        origin = airport;
      }
      else if (airport.icao.equals(destinationIcao)) {
        destination = airport;
      }
    }
  }

  @Override
  public String toString() {
    return "Route{" +
            "id=" + id +
            ", distance=" + distance +
            ", etopsRequirement=" + etopsRequirement +
            ", originIcao='" + originIcao + '\'' +
            ", origin=" + origin +
            ", destinationIcao='" + destinationIcao + '\'' +
            ", destination=" + destination +
            '}';
  }
}
