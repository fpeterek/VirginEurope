package org.fpeterek.virgineurope.orm.sql.custom;

import org.fpeterek.virgineurope.orm.sql.CustomQuery;
import org.fpeterek.virgineurope.validators.IcaoValidator;
import org.yaml.snakeyaml.Yaml;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightSearchQuery extends CustomQuery {

  String query;

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

    // Validate airport codes to prevent SQL Injection
    if (!IcaoValidator.isValid(orig) || !IcaoValidator.isValid(dest)) {
      throw new IllegalArgumentException("Invalid icao code");
    }
    query = "SELECT FindFlights(" + quote(orig) + ", " + quote(dest) + ");";
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
