package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

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

  private static void checkVal(int val, String valname) {
    if (val < 0) {
      throw new IllegalArgumentException(valname + " cannot be less than zero. ");
    }
  }

  public int getId() { return id; }
  public int getDistance() { return distance; }
  public int getEtopsRequirement() { return etopsRequirement; }
  public String getOriginIcao() { return originIcao; }
  public Airport getOrigin() { return origin; }
  public String getDestinationIcao() { return destinationIcao; }
  public Airport getDestination() { return destination; }

  public void setDistance(int dist) {
    checkVal(dist, "Distance");
    distance = dist;
  }

  public void setEtopsRequirement(int requirement) {
    checkVal(requirement, "ETOPS requirement");
    etopsRequirement = requirement;
  }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.route.id.eq(id));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.route.origin, originIcao)
        .set(VU.route.destination, destinationIcao)
        .set(VU.route.etopsRequirement, etopsRequirement)
        .set(VU.route.distance, distance)
        .where(VU.route.id.eq(id));
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.route.origin, VU.route.destination, VU.route.etopsRequirement, VU.route.distance)
        .values(originIcao, destinationIcao, etopsRequirement, distance);
  }

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
