package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.tables.*;

public class VU {

  public static AircraftTable aircraft = new AircraftTable();
  public static AircraftModelTable aircraftModel = new AircraftModelTable();
  public static AirportTable airport = new AirportTable();
  public static CrewTable crew = new CrewTable();
  public static CrewOnFlightTable crewOnFlight = new CrewOnFlightTable();
  public static FlightTable flight = new FlightTable();
  public static OperatedFlightTable operatedFlight = new OperatedFlightTable();
  public static PassengerTable passenger = new PassengerTable();
  public static FlightTicketTable flightTicket = new FlightTicketTable();
  public static PilotTable pilot = new PilotTable();
  public static PilotOnFlightTable pilotOnFlight = new PilotOnFlightTable();
  public static RouteTable route = new RouteTable();

}
