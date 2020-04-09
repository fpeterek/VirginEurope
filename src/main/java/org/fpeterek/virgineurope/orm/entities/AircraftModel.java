package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.EtopsCertified;

public class AircraftModel extends Entity {

  String designator;
  String manufacturer;
  String family;
  String fullType;
  EtopsCertified etopsCertified;
  int etopsRating;
  int rangeNmi;
  int mtow;

  public AircraftModel(String des, String man, String fam, String ft, EtopsCertified certified,
                  int rating, int range, int mtow) {

    designator = des;
    manufacturer = man;
    family = fam;
    fullType = ft;
    etopsCertified = certified;
    etopsRating = rating;
    rangeNmi = range;
    this.mtow = mtow;

  }

}
