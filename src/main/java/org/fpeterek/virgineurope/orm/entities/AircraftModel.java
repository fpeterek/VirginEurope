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

  public String getDesignator() { return designator; }
  public String getManufacturer() { return manufacturer; }
  public String getFamily() { return family; }
  public String getFullType() { return fullType; }
  public EtopsCertified isEtopsCertified() { return etopsCertified; }
  public int getEtopsRating() { return etopsRating; }
  public int getRange() { return rangeNmi; }
  public int getMtow() { return mtow; }

  @Override
  public String toString() {
    return "AircraftModel{" +
            "designator='" + designator + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", family='" + family + '\'' +
            ", fullType='" + fullType + '\'' +
            ", etopsCertified=" + etopsCertified +
            ", etopsRating=" + etopsRating +
            ", rangeNmi=" + rangeNmi +
            ", mtow=" + mtow +
            '}';
  }
}
