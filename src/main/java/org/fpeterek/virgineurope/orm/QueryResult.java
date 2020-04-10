package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryResult {

  List<Table> includedTables;

  List<Aircraft> aircraft;
  List<AircraftModel> aircraftModels;
  List<Airport> airports;
  List<Crew> crew;
  List<Flight> flights;
  List<FlightTicket> tickets;
  List<OperatedFlight> operatedFlights;
  List<Passenger> passengers;
  List<Pilot> pilots;
  List<Route> routes;

  private void addToCorrespondingList(Entity entity, Table table) {

    if (table == VU.aircraft) {
      aircraft.add((Aircraft)entity);
    }
    else if (table == VU.aircraftModel) {
      aircraftModels.add((AircraftModel)entity);
    }
    else if (table == VU.airport) {
      airports.add((Airport)entity);
    }
    else if (table == VU.crew) {
      crew.add((Crew)entity);
    }
    else if (table == VU.flight) {
      flights.add((Flight)entity);
    }
    else if (table == VU.passengerOnFlight) {
      tickets.add((FlightTicket)entity);
    }
    else if (table == VU.operatedFlight) {
      operatedFlights.add((OperatedFlight)entity);
    }
    else if (table == VU.passenger) {
      passengers.add((Passenger)entity);
    }
    else if (table == VU.pilot) {
      pilots.add((Pilot)entity);
    }
    else if (table == VU.route) {
      routes.add((Route)entity);
    }

  }

  public QueryResult(ResultSet res, List<Table> tables) throws SQLException {

    includedTables = tables;

    while (res.next()) {

      int offset = 0;

      for (var table : includedTables) {
        addToCorrespondingList(table.parseFrom(res, offset), table);
        offset += table.offset();
      }

    }

  }

  public List<Aircraft> getAircraft() {
    return new ArrayList<>();
  }

  public List<AircraftModel> getAircraftModels() {
    return new ArrayList<>();
  }

  public List<Airport> getAirports() {
    return new ArrayList<>();
  }

  public List<Crew> getCrew() {
    return new ArrayList<>();
  }

  public List<Flight> getFlights() {
    return new ArrayList<>();
  }

  public List<FlightTicket> getFlightTickets() {
    return new ArrayList<>();
  }

  public List<OperatedFlight> getOperatedFlights() {
    return new ArrayList<>();
  }

  public List<Passenger> getPassengers() {
    return new ArrayList<>();
  }

  public List<Pilot> getPilots() {
    return new ArrayList<>();
  }

  public List<Route> getRoutes() {
    return new ArrayList<>();
  }

}
