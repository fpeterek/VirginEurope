package org.fpeterek.virgineurope.orm.sql.custom;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;
import org.fpeterek.virgineurope.validators.IcaoValidator;
import org.yaml.snakeyaml.Yaml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightSearchQuery extends CustomQuery {

  private final String query;
  private final String origin;
  private final String destination;

  private List<String> firstFlight;
  private List<String> secondFlight;
  private boolean isSingleSegmentFlight = true;

  public boolean isSingleSegment() { return isSingleSegmentFlight; }
  public List<String> getFirstFlights() { return firstFlight; }
  public List<String> getSecondFlights() { return secondFlight; }

  private static String quote(String str) {
    return '\'' + str + '\'';
  }

  public FlightSearchQuery(String orig, String dest) {

    if (!IcaoValidator.isValid(orig) || !IcaoValidator.isValid(dest)) {
      throw new IllegalArgumentException("Invalid icao code");
    }

    origin = orig;
    destination = dest;
    query = "SELECT FindFlights(?, ?);";
  }

  @Override
  public PreparedStatement prepare(Connection connection) throws SQLException {
    var sm = connection.prepareStatement(query);
    sm.setString(1, origin);
    sm.setString(2, destination);
    return sm;
  }

  @Override
  public CustomQuery parseResult(ResultSet rs) throws SQLException {

    rs.next();

    var json = rs.getString(1);
    var yaml = new Yaml();
    var parsed = (Map<String, List<String>>)yaml.load(json);

    firstFlight = parsed.getOrDefault("first_flight", new ArrayList<>());
    secondFlight = parsed.getOrDefault("second_flight", new ArrayList<>());
    isSingleSegmentFlight = !parsed.containsKey("second_flight");

    return this;
  }

  @Override
  public String query() {
    return query;
  }

  @Override
  public String toString() {
    return "FlightSearch{" +
            "firstFlight=" + firstFlight +
            ", secondFlight=" + secondFlight +
            ", isSingleSegmentFlight=" + isSingleSegmentFlight +
            '}';
  }
}
