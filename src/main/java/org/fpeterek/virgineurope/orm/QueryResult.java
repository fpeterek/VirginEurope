package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.*;
import org.fpeterek.virgineurope.orm.tables.*;

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

    if (table instanceof AircraftTable) {
      aircraft.add((Aircraft)entity);
    }
    else if (table instanceof AircraftModelTable) {
      aircraftModels.add((AircraftModel)entity);
    }
    else if (table instanceof AirportTable) {
      airports.add((Airport)entity);
    }
    else if (table instanceof CrewTable) {
      crew.add((Crew)entity);
    }
    else if (table instanceof FlightTable) {
      flights.add((Flight)entity);
    }
    else if (table instanceof PassengerOnFlightTable) {
      tickets.add((FlightTicket)entity);
    }
    else if (table instanceof OperatedFlightTable) {
      operatedFlights.add((OperatedFlight)entity);
    }
    else if (table instanceof PassengerTable) {
      passengers.add((Passenger)entity);
    }
    else if (table instanceof PilotTable) {
      pilots.add((Pilot)entity);
    }
    else if (table instanceof RouteTable) {
      routes.add((Route)entity);
    }

  }

  public QueryResult(ResultSet res, List<Table> tables) throws SQLException {

    includedTables = tables;
    var entities = new ArrayList<Entity>();

    while (res.next()) {

      int offset = 0;
      entities.clear();

      for (var table : includedTables) {
        Entity entity = table.parseFrom(res, offset);
        entities.add(entity);
        addToCorrespondingList(entity, table);
        offset += table.offset();
      }

      for (Entity entity : entities) {
        for (Entity e : entities) {
          entity.add(e);
        }
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
