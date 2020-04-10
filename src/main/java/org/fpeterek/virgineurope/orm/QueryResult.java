package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryResult {

  List<Table> includedTables;

  List<Aircraft> aircraft              = new ArrayList<>();
  List<AircraftModel> aircraftModels   = new ArrayList<>();
  List<Airport> airports               = new ArrayList<>();
  List<Crew> crew                      = new ArrayList<>();
  List<Flight> flights                 = new ArrayList<>();
  List<FlightTicket> tickets           = new ArrayList<>();
  List<OperatedFlight> operatedFlights = new ArrayList<>();
  List<Passenger> passengers           = new ArrayList<>();
  List<Pilot> pilots                   = new ArrayList<>();
  List<Route> routes                   = new ArrayList<>();

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
    return aircraft;
  }

  public List<AircraftModel> getAircraftModels() {
    return aircraftModels;
  }

  public List<Airport> getAirports() {
    return airports;
  }

  public List<Crew> getCrew() {
    return crew;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  public List<FlightTicket> getFlightTickets() {
    return tickets;
  }

  public List<OperatedFlight> getOperatedFlights() {
    return operatedFlights;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public List<Pilot> getPilots() {
    return pilots;
  }

  public List<Route> getRoutes() {
    return routes;
  }

}
