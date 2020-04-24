package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.*;
import org.fpeterek.virgineurope.orm.tables.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  Map<String, Aircraft> aircraftMap              = new HashMap<>();
  Map<String, AircraftModel> modelMap            = new HashMap<>();
  Map<String, Airport> airportMap                = new HashMap<>();
  Map<Integer, Crew> crewMap                     = new HashMap<>();
  Map<String, Flight> flightMap                  = new HashMap<>();
  Map<Integer, FlightTicket> ticketMap           = new HashMap<>();
  Map<Integer, OperatedFlight> operatedFlightMap = new HashMap<>();
  Map<Integer, Passenger> passengerMap           = new HashMap<>();
  Map<Integer, Pilot> pilotMap                   = new HashMap<>();
  Map<Integer, Route> routeMap                   = new HashMap<>();

  private void mapsToLists() {

    aircraftMap.forEach((k, ac) -> aircraft.add(ac));
    modelMap.forEach((k, model) -> aircraftModels.add(model));
    airportMap.forEach((k, airport) -> airports.add(airport));
    crewMap.forEach((k, cr) -> crew.add(cr));
    flightMap.forEach((k, fl) -> flights.add(fl));
    ticketMap.forEach((k, ticket) -> tickets.add(ticket));
    operatedFlightMap.forEach((k, oper) -> operatedFlights.add(oper));
    passengerMap.forEach((k, pax) -> passengers.add(pax));
    pilotMap.forEach((k, pilot) -> pilots.add(pilot));
    routeMap.forEach((k, route) -> routes.add(route));

    aircraftMap       = null;
    modelMap          = null;
    airportMap        = null;
    crewMap           = null;
    flightMap         = null;
    ticketMap         = null;
    operatedFlightMap = null;
    passengerMap      = null;
    pilotMap          = null;
    routeMap          = null;

  }

  /* Maybe could be generified, but I'm not doing that now */
  /* TODO: Look for a more generic approach                */
  private Entity addToCorrespondingMap(Entity entity, Table table) {

    if (table instanceof AircraftTable) {
      var ac = (Aircraft)entity;
      var retval = aircraftMap.putIfAbsent(ac.getIdentifier(), ac);
      return retval == null ? ac : retval;
    }
    else if (table instanceof AircraftModelTable) {
      var acModel = (AircraftModel)entity;
      var retval = modelMap.putIfAbsent(acModel.getDesignator(), acModel);
      return retval == null ? acModel : retval;
    }
    else if (table instanceof AirportTable) {
      var ap = (Airport)entity;
      var retval = airportMap.putIfAbsent(ap.getIcao(), ap);
      return retval == null ? ap : retval;
    }
    else if (table instanceof CrewTable) {
      var cr = (Crew)entity;
      var retval = crewMap.putIfAbsent(cr.getId(), cr);
      return retval == null ? cr : retval;
    }
    else if (table instanceof FlightTable) {
      var fl = (Flight)entity;
      var retval = flightMap.putIfAbsent(fl.getFlightId(), fl);
      return retval == null ? fl : retval;
    }
    else if (table instanceof FlightTicketTable) {
      var tk = (FlightTicket)entity;
      var retval = ticketMap.putIfAbsent(tk.getTicketId(), tk);
      return retval == null ? tk : retval;
    }
    else if (table instanceof OperatedFlightTable) {
      var of = (OperatedFlight)entity;
      var retval = operatedFlightMap.putIfAbsent(of.getId(), of);
      return retval == null ? of : retval;
    }
    else if (table instanceof PassengerTable) {
      var pax = (Passenger)entity;
      var retval = passengerMap.putIfAbsent(pax.getId(), pax);
      return retval == null ? pax : retval;
    }
    else if (table instanceof PilotTable) {
      var pl = (Pilot)entity;
      var retval = pilotMap.putIfAbsent(pl.getId(), pl);
      return retval == null ? pl : retval;
    }
    else if (table instanceof RouteTable) {
      var rt = (Route)entity;
      var retval = routeMap.putIfAbsent(rt.getId(), rt);
      return retval == null ? rt : retval;
    }

    return entity;

  }

  public QueryResult(ResultSet res, List<Table> tables) throws SQLException {

    includedTables = tables;
    var entities = new ArrayList<Entity>();

    while (res.next()) {

      int offset = 0;
      entities.clear();

      for (var table : includedTables) {
        Entity entity = table.parseFrom(res, offset);
        if (entity == null) {
          continue;
        }

        entity = addToCorrespondingMap(entity, table);

        entities.add(entity);
        offset += table.offset();
      }

      for (Entity entity : entities) {
        for (Entity e : entities) {
          entity.add(e);
        }
      }

    }

    mapsToLists();

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
