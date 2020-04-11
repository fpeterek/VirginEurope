package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

import java.util.ArrayList;
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

  public int getId() { return id; }
  public DateTime getActualDeparture() { return actualDeparture; }
  public DateTime getActualArrival() { return actualArrival; }
  public String getFlightId() { return flightId; }
  public Flight getFlight() { return flight; }
  public String getAircraftId() { return aircraftId; }
  public Aircraft getAircraft() { return aircraft; }
  public DateTime getDate() { return date; }
  public List<Crew> getCrew() { return crew; }
  public List<FlightTicket> getFlightTickets() { return tickets; }
  public List<Pilot> getPilots() { return pilots; }


  private void addCrew(Crew crewMember) {
    if (crew == null) {
      crew = new ArrayList<>();
    }
    crew.add(crewMember);
  }

  private void addTicket(FlightTicket ticket) {
    if (tickets == null) {
      tickets = new ArrayList<>();
    }
    tickets.add(ticket);
  }

  private void addPilot(Pilot pilot) {
    if (pilots == null) {
      pilots = new ArrayList<>();
    }
    pilots.add(pilot);
  }

  @Override
  public void add(Entity entity) {
    if (entity instanceof Aircraft) {
      aircraft = (Aircraft)entity;
    }
    else if (entity instanceof Crew) {
      addCrew((Crew)entity);
    }
    else if (entity instanceof Pilot) {
      addPilot((Pilot)entity);
    }
    else if (entity instanceof FlightTicket) {
      addTicket((FlightTicket)entity);
    }
  }

  @Override
  public String toString() {
    return "OperatedFlight{" +
            "id=" + id +
            ", actualDeparture=" + actualDeparture +
            ", actualArrival=" + actualArrival +
            ", flightId='" + flightId + '\'' +
            ", flight=" + flight +
            ", aircraftId='" + aircraftId + '\'' +
            ", aircraft=" + aircraft +
            ", date=" + date +
            ", crew=" + crew +
            ", tickets=" + tickets +
            ", pilots=" + pilots +
            '}';
  }
}
