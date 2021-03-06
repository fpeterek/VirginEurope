package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class OperatedFlight extends Entity {

  int id;
  LocalTime actualDeparture;
  LocalTime actualArrival;
  String flightId;
  Flight flight;
  String aircraftId;
  Aircraft aircraft;
  LocalDate date;

  List<Crew> crew;
  List<FlightTicket> tickets;
  List<Pilot> pilots;

  public OperatedFlight(int identifier, LocalTime departure, LocalTime arrival, String flightNo,
                        Flight fl, String aircraftReg, Aircraft ac, LocalDate flightDate,
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
  public LocalTime getActualDeparture() { return actualDeparture; }
  public LocalTime getActualArrival() { return actualArrival; }
  public String getFlightId() { return flightId; }
  public Flight getFlight() { return flight; }
  public String getAircraftId() { return aircraftId; }
  public Aircraft getAircraft() { return aircraft; }
  public LocalDate getDate() { return date; }
  public List<Crew> getCrew() { return crew; }
  public List<FlightTicket> getFlightTickets() { return tickets; }
  public List<Pilot> getPilots() { return pilots; }

  public void setActualDeparture(LocalTime time) { actualDeparture = time; }
  public void setActualArrival(LocalTime time) { actualArrival = time; }
  public void setAircraftId(String id) { aircraftId = id; }
  public void setDate(LocalDate newDate) { date = newDate; }

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

  private String formatTime(DateTime time) {
    if (time == null) {
      return null;
    }
    return time.toString("HH:mm:ss");
  }

  private String formattedDate() {
    return date.toString("yyyy-MM-dd");
  }

  @Override
  public void formDelete(Delete query) {
    query.where(VU.operatedFlight.id.eq(id));
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.operatedFlight.aircraftIdentifier, aircraftId)
        .set(VU.operatedFlight.actualArrival, actualArrival)
        .set(VU.operatedFlight.actualDeparture, actualDeparture)
        .set(VU.operatedFlight.date, date)
        .set(VU.operatedFlight.flightId, flightId)

        .where(VU.operatedFlight.id.eq(id));
  }

  private void insertNoTime(Insert query) {
    query.attributes(VU.operatedFlight.aircraftIdentifier, VU.operatedFlight.date, VU.operatedFlight.flightId)
        .values(aircraftId, date, flightId);
  }

  private void insertNoArrival(Insert query) {
    query.attributes(VU.operatedFlight.aircraftIdentifier, VU.operatedFlight.actualDeparture, VU.operatedFlight.date,
        VU.operatedFlight.flightId)
        .values(aircraftId, actualDeparture, date, flightId);
  }

  private void insertWithTime(Insert query) {
    query.attributes(VU.operatedFlight.aircraftIdentifier, VU.operatedFlight.actualArrival,
        VU.operatedFlight.actualDeparture, VU.operatedFlight.date, VU.operatedFlight.flightId)
        .values(aircraftId, actualArrival, actualDeparture, date, flightId);
  }

  /* Time can be null, this helps us avoid problems with nulls */
  @Override
  public void formInsert(Insert query) {
    if (actualDeparture != null && actualArrival != null) {
      insertWithTime(query);
    } else if (actualDeparture != null) {
      insertNoArrival(query);
    } else {
      insertNoTime(query);
    }
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
    } else if (entity instanceof Flight) {
      flight = (Flight)entity;
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
