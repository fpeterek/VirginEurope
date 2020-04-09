package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

import java.util.List;

public class OperatedFlight extends Entity {

  int id;
  DateTime actualDeparture;
  DateTime actualArrival;
  String flightId;
  Flight flight;
  String aircraftId;
  Aircraft aircraft;
  DateTime date;

  List<Crew> crew;
  List<FlightTicket> tickets;
  List<Pilot> pilots;

  public OperatedFlight(int identifier, DateTime departure, DateTime arrival, String flightNo,
                        Flight fl, String aircraftReg, Aircraft ac, DateTime flightDate,
                        List<Crew> crewOnFlight, List<FlightTicket> flightTickets,
                        List<Pilot> pilotsOnFlight) {
    id = identifier;
    actualDeparture = departure;
    actualArrival = arrival;
    flightId = flightNo;
    flight = fl;
    aircraftId = aircraftReg;
    aircraft = ac;
    date = flightDate;
    crew = crewOnFlight;
    tickets = flightTickets;
    pilots = pilotsOnFlight;

  }

}
