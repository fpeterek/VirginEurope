package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.EtopsCertified;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

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
  public void formDelete(Delete query) {
    query.where(VU.aircraftModel.designator.eq(designator));
  }

  @Override
  public void formUpdate(Update query) {
    query
            .set(VU.aircraftModel.fullType, fullType)
            .set(VU.aircraftModel.etopsCertified, etopsCertified.dbValue())
            .set(VU.aircraftModel.etopsRating, String.valueOf(etopsRating))
            .set(VU.aircraftModel.family, family)
            .set(VU.aircraftModel.manufacturer, manufacturer)
            .set(VU.aircraftModel.rangeNmi, String.valueOf(rangeNmi))
            .set(VU.aircraftModel.mtow, String.valueOf(mtow))
            .where(VU.aircraftModel.designator.eq(designator));

  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.aircraftModel.designator, VU.aircraftModel.fullType,
            VU.aircraftModel.etopsCertified, VU.aircraftModel.etopsRating,
            VU.aircraftModel.family, VU.aircraftModel.manufacturer,
            VU.aircraftModel.rangeNmi, VU.aircraftModel.mtow)
            .values(designator, fullType, etopsCertified.dbValue(),
                    String.valueOf(etopsRating), family, manufacturer,
                    String.valueOf(rangeNmi), String.valueOf(mtow));
  }

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
