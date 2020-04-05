package org.fpeterek.virgineurope.orm.entities;

import org.joda.time.DateTime;

import java.util.List;

public class OperatedFlight {

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

}
