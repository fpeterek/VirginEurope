package org.fpeterek.virgineurope.orm.sql.custom;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;
import org.fpeterek.virgineurope.validators.FlightNumberValidator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaxPerClassQuery extends CustomQuery {

  private int ecoPax = 0;
  private int busPax = 0;
  private int firstPax = 0;

  private final String select;

  public PaxPerClassQuery(String flight) {

    if (!FlightNumberValidator.isValid(flight)) {
      throw new IllegalArgumentException("Invalid flight number: '" + flight + "'");
    }

    var sb = new StringBuilder();

    sb.append("SELECT pof.class, COUNT(pof.passenger_id) ")
      .append("FROM operated_flight ")
      .append("JOIN passenger_on_flight pof ON operated_flight.operated_id = pof.operated_id ")
      .append("WHERE flight_id='").append(flight).append("' ")
      .append("GROUP BY pof.class ")
      .append("ORDER BY class;");

    select = sb.toString();

  }

  private void update(String cls, int pax) {
    switch (cls) {
      case "business":
        busPax = pax;
        break;
      case "first":
        firstPax = pax;
        break;
      case "economy":
        ecoPax = pax;
        break;
    }
  }

  @Override
  public CustomQuery parseResult(ResultSet rs) throws SQLException {

    while (rs.next()) {
      var cls = rs.getString(1);
      var pax = rs.getInt(2);
      update(cls, pax);
    }

    return this;
  }

  @Override
  public String query() {
    return select;
  }

  public int getEconomyPassengers() { return ecoPax; }
  public int getBusinessPassengers() { return busPax; }
  public int getFirstPassengers() { return firstPax; }
}
