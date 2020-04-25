package org.fpeterek.virgineurope.orm.sql.custom;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;
import org.fpeterek.virgineurope.validators.FlightNumberValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaxPerClassQuery extends CustomQuery {

  private int ecoPax = 0;
  private int busPax = 0;
  private int firstPax = 0;

  private final String select;
  private final String flightId;

  public PaxPerClassQuery(String flight) {

    // Validate flight number to prevent SQL Injection
    if (!FlightNumberValidator.isValid(flight)) {
      throw new IllegalArgumentException("Invalid flight number: '" + flight + "'");
    }

    flightId = flight;

    select = "SELECT ft.class, COUNT(ft.passenger_id) " +
        "FROM operated_flight " +
        "JOIN flight_ticket ft ON operated_flight.operated_id = ft.operated_id " +
        "WHERE flight_id=?" +
        "GROUP BY ft.class " +
        "ORDER BY class;";

  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var sm = connection.prepareStatement(select);
    sm.setString(1, flightId);
    return sm;
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
