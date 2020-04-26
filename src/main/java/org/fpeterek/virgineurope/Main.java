package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.common.CrewRole;
import org.fpeterek.virgineurope.common.SeatType;
import org.fpeterek.virgineurope.common.Seniority;
import org.fpeterek.virgineurope.common.TravelClass;
import org.fpeterek.virgineurope.orm.Database;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.entities.*;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Select;
import org.fpeterek.virgineurope.orm.sql.Update;
import org.fpeterek.virgineurope.orm.sql.custom.AssignPilotsQuery;
import org.fpeterek.virgineurope.orm.sql.custom.CancelFlightQuery;
import org.fpeterek.virgineurope.orm.sql.custom.FlightSearchQuery;
import org.fpeterek.virgineurope.orm.sql.custom.PaxPerClassQuery;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.SQLException;

import static kotlin.io.ConsoleKt.readLine;

public class Main {

  public static void waitForInput() {
    System.out.println("Check the database manually or continue execution by pressing enter...");
    readLine();
  }

  public static void main(String[] args) {

    builderTest();

    try {
      var db = new Database();
      passengerTest(db);
      airportSearchTest(db);
      fleetManagementTest(db);
      routeManagementTest(db);
      flightManagementTest(db);
      employeeManagementTest(db);
      flightTicketManagementTest(db);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  public static void flightTicketManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 8.1 through 8.4 - Flight Ticket management\n\n");

    var me = new Passenger(0, "Filip", "Peterek",
        "vegetarian", SeatType.Window, null);
    db.execute(Insert.into(VU.passenger).row(me));
    me = db.execute(Select.from(VU.passenger).where(VU.passenger.lastName.eq("Peterek")))
        .getPassengers().get(0);

    System.out.println("Function 6.1 - purchasing tickets\n");

    var ticket = new FlightTicket(0, "vegan", "5A", TravelClass.Business,
        32, 1, null, me.getId(), me);

    var insert = Insert.into(VU.flightTicket).row(ticket);
    System.out.println("Query: " + ticket + "\n");
    System.out.println("Inserted " + db.execute(insert) + " values");


    ticket.setOperatedId(3);
    ticket.setTravelClass(TravelClass.Economy);
    ticket.setMeal("vegetarian");
    insert = Insert.into(VU.flightTicket).row(ticket);
    db.execute(insert);

    ticket.setOperatedId(5);
    ticket.setTravelClass(TravelClass.Business);
    ticket.setSeat("3C");
    insert = Insert.into(VU.flightTicket).row(ticket);
    db.execute(insert);

    System.out.println("Function 6.2 - printing tickets\n");

    System.out.println("Printing my flights...");

    var select = Select.from(VU.flightTicket)
        .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
        .where(VU.passenger.lastName.eq("Peterek"));

    System.out.println("Query: " + select + "\n");

    db.execute(select).getFlightTickets().forEach(System.out::println);

    waitForInput();

    System.out.println("Function 6.3 - updating tickets\n");

    select = Select.from(VU.flightTicket)
        .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
        .where(VU.passenger.lastName.eq("Peterek")
            .and(VU.flightTicket.travelClass.eq("economy")));

    ticket = db.execute(select).getFlightTickets().get(0);

    System.out.println("I don't necessarily fit in economy seats, so let's upgrade my flight" +
        ", and change my meal while I'm at it.\n");

    ticket.setTravelClass(TravelClass.Business);
    ticket.setBaggageAllowance(64);
    ticket.setMeal("halal");

    var update = Update.table(VU.flightTicket).row(ticket);
    System.out.println("Query: " + update + "\n");
    System.out.println("Updated " + db.execute(update) + " rows.\n");

    System.out.println("If we fetch my flights again, I should only ever fly business. " +
        "I should also have halal meal set on one of my flights.\n");

    select = Select.from(VU.flightTicket)
        .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
        .where(VU.passenger.lastName.eq("Peterek"));
    db.execute(select).getFlightTickets().forEach(System.out::println);

    waitForInput();

    System.out.println("Function 6.4 - deleting tickets\n");

    System.out.println("Let's delete the ticket updated in the previous step...\n");

    var delete = Delete.from(VU.flightTicket).row(ticket);
    System.out.println("Query: " + delete);
    System.out.println("\nDeleted " + db.execute(delete) + " values. \n");

    System.out.println("Number of tickets I currently hold: "
        + db.execute(select).getFlightTickets().size());

    waitForInput();

    System.out.println("Resetting DB to previous state...");
    db.execute(Delete.from(VU.flightTicket).where(VU.flightTicket.passengerId.eq(me.getId())));
    db.execute(Delete.from(VU.passenger).row(me));

  }

  public static void employeeManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 6.1 through 6.4 - Employee management\n\n");

    System.out.println("Function 6.1 - adding employees\n");

    var pilot = new Pilot(0, "Solr", "Elasticsearch", "A350", false, null);
    var insert = Insert.into(VU.pilot).row(pilot);
    System.out.println("Query: " + insert + "\n");
    System.out.println("Inserted " + db.execute(insert) + " rows.\n");

    var fa = new Crew(0, "MariaDB", "HBase", CrewRole.Attendant, Seniority.Senior, null);
    insert = Insert.into(VU.crew).row(fa);
    System.out.println("Query: " + insert + "\n");
    System.out.println("Inserted " + db.execute(insert) + " rows.\n");

    System.out.println("Function 6.2 - accessing employee data\n");
    var select = Select.from(VU.pilot)
        .where(VU.pilot.lastName.eq("Elasticsearch"));
    System.out.println("Query: " + select + "\n");
    pilot = db.execute(select).getPilots().get(0);
    System.out.println(pilot);

    select = Select.from(VU.crew)
        .where(VU.crew.lastName.eq("HBase"));
    System.out.println("\nQuery: " + select + "\n");
    fa = db.execute(select).getCrew().get(0);
    System.out.println(fa);

    waitForInput();

    System.out.println("\nFunction 6.3 - updating employee data\n");

    System.out.println("Let's say our pilot got a promotion and changed their name to Elastic");
    System.out.println("Our flight attendant switched to using Postgres");

    pilot.setLastName("Elastic");
    pilot.setCaptain(true);
    fa.setFirstName("PostgreSQL");

    var update = Update.table(VU.pilot).row(pilot);
    System.out.println("Updated " + db.execute(update) + " rows \n");
    update = Update.table(VU.crew).row(fa);
    System.out.println("Updated " + db.execute(update) + " rows \n");

    System.out.println("Printing updated values...\n");

    db.execute(Select.from(VU.pilot).where(VU.pilot.lastName.eq("Elastic")))
        .getPilots().forEach(System.out::println);
    db.execute(Select.from(VU.crew).where(VU.crew.lastName.eq("HBase")))
        .getCrew().forEach(System.out::println);

    waitForInput();

    System.out.println("\nFunction 6.4 - deleting employee data\n");

    var delete = Delete.from(VU.pilot).row(pilot);
    System.out.println("Query: " + delete);
    var deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " values.");

    delete = Delete.from(VU.crew).row(fa);
    System.out.println("Query: " + delete);
    deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " values.");

    var esCount = db.execute(Select.from(VU.pilot).where(VU.pilot.lastName.eq("Elastic")))
        .getPilots().size();
    var hbaseCount = db.execute(Select.from(VU.crew).where(VU.crew.lastName.eq("HBase")))
        .getCrew().size();

    System.out.println("Number of pilots whose last name is Elastic: " + esCount);
    System.out.println("Number of flight attendants whose last name is HBase: " + hbaseCount);

    waitForInput();
  }

  public static void flightManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 5.1 through 5.4 and 7.1 through 7.4 - Flight management, assigning crew\n");

    System.out.println("Function 5.1 - Creating operated flights\n\n");

    var of = new OperatedFlight(0, null, null, "VU0139", null, "OK-XWB", null,
        new LocalDate(2020, 5, 18), null, null, null);

    System.out.println(DateTime.parse("2020-05-18", DateTimeFormat.forPattern("yyyy-MM-dd")));

    var insert = Insert.into(VU.operatedFlight).row(of);

    System.out.println("Query: " + insert.toFormattedString());
    var inserted = db.execute(insert);
    System.out.println("\nInserted: " + inserted + " queries\n\n");

    System.out.println("Function 5.2 - Listing operated flights\n\n");

    System.out.println("We will only fetch the newly inserted flight to avoid cluttering stdout.\n");

    var sel = Select.from(VU.operatedFlight).where(VU.operatedFlight.aircraftIdentifier.eq("OK-XWB").and(
        VU.operatedFlight.date.eq(new LocalDate(2020, 5, 18)))
        .and(VU.operatedFlight.flightId.eq("VU0139")));

    System.out.println(sel.build() + "\n");
    System.out.println(sel.toFormattedString() + "\n");

    var fl = db.execute(sel).getOperatedFlights().get(0);

    System.out.println(fl + "\n");

    waitForInput();
    System.out.println("Function 5.3 - Editing operated flights\n\n");

    // Time doesn't matter here
    fl.setDate(new LocalDate(2020, 6, 20));
    // Dates don't matter here, only time does
    fl.setActualDeparture(new LocalTime(13, 20, 0));
    fl.setActualArrival(new LocalTime(14, 50, 30));

    var update = Update.table(VU.operatedFlight).row(fl);

    System.out.println("Update query: " + update.toFormattedString() + "\n");
    var updated = db.execute(update);
    System.out.println("Updated " + updated + " rows");

    fl = db.execute(Select.from(VU.operatedFlight).where(VU.operatedFlight.id.eq(fl.getId()))).getOperatedFlights().get(0);

    System.out.println("\nFlight refetched from DB:\n" + fl);

    waitForInput();

    System.out.println("Function 5.4 - Cancelling flights\n\n");

    System.out.println("Before deleting a flight from DB, we must first rebook passengers.\n");

    System.out.println("Setting up db...");

    System.out.println("Creating flights...");
    of = new OperatedFlight(0, null, null, "VU0139", null, "OK-XWC", null,
        LocalDate.parse("2020-06-21", DateTimeFormat.forPattern("yyyy-MM-dd")), null, null, null);
    db.execute(Insert.into(VU.operatedFlight).row(of));
    of = new OperatedFlight(0, null, null, "VU0139", null, "OK-XWD", null,
        LocalDate.parse("2020-06-22", DateTimeFormat.forPattern("yyyy-MM-dd")), null, null, null);
    db.execute(Insert.into(VU.operatedFlight).row(of));

    System.out.println("Adding passengers...");

    var ticket = new FlightTicket(0, "default", "5A", TravelClass.Business, 64, fl.getId(), null, 1, null);
    db.execute(Insert.into(VU.flightTicket).row(ticket));
    ticket.setPassengerId(2);
    db.execute(Insert.into(VU.flightTicket).row(ticket));
    ticket.setPassengerId(3);
    db.execute(Insert.into(VU.flightTicket).row(ticket));
    ticket.setOperatedId(fl.getId()+1);
    db.execute(Insert.into(VU.flightTicket).row(ticket));

    System.out.println("Passengers on cancelled flight: \n");
    db.execute(Select.from(VU.flightTicket).where(VU.flightTicket.operatedId.eq(fl.getId()))).getFlightTickets()
    .forEach(System.out::println);

    System.out.println();
    waitForInput();

    System.out.println("\n\nThere should now be three passengers on the cancelled flight. Two of these should get " +
        "rebooked onto the next flight. The third passenger should actually already own a ticket for the second flight" +
        ", causing this passenger to be rebooked on the third flight, not on the second flight. \n");

    System.out.println("Cancelling flight...");
    var cancelResult = (CancelFlightQuery)db.execute(new CancelFlightQuery(fl.getId()));
    System.out.println(cancelResult);

    System.out.println("\nThe flight can now be safely deleted from DB");
    var delete = Delete.from(VU.operatedFlight).row(fl);
    System.out.println("Query: " + delete);
    var deleted = db.execute(delete);

    System.out.println("\nDeleted " + deleted + " values.\n");

    System.out.println("Let's check if the passengers have been properly rebooked...\n");
    System.out.println("Second flight (should have 3 passengers):");
    db.execute(Select.from(VU.flightTicket).where(VU.flightTicket.operatedId.eq(fl.getId()+1))).getFlightTickets()
        .forEach(System.out::println);

    System.out.println("Third flight (should have 1 passenger):");
    db.execute(Select.from(VU.flightTicket).where(VU.flightTicket.operatedId.eq(fl.getId()+2))).getFlightTickets()
        .forEach(System.out::println);

    System.out.println("\n\nFunction 7.1 - assigning pilots and crew\n\n");
    fl = db.execute(Select.from(VU.operatedFlight).where(VU.operatedFlight.id.eq(fl.getId()+1))).getOperatedFlights().get(0);

    System.out.println("To assign pilots automatically, we can use the 'AssignPilots' stored function.\n");
    System.out.println("We will assign pilots on the second newly created flight (the flight on which the majority of the" +
        "passengers was rebooked).");
    System.out.println("If we try to fetch pilots from DB, we'll see there are no pilots assigned for this flight:");
    db.execute(Select.from(VU.pilotOnFlight).join(VU.pilot).on(VU.pilotOnFlight.pilotId.eq(VU.pilot.id))
        .where(VU.pilotOnFlight.operatedId.eq(fl.getId()))).getPilots()
        .forEach(System.out::println);

    System.out.println("\nNow, let's try calling AssignPilots().\n");
    waitForInput();
    var res = (AssignPilotsQuery)db.execute(new AssignPilotsQuery(fl.getId()));
    System.out.println(res);

    System.out.println("\nAnd fetch pilots again:");

    System.out.println(Select.from(VU.pilotOnFlight).join(VU.pilot).on(VU.pilotOnFlight.pilotId.eq(VU.pilot.id))
        .where(VU.pilotOnFlight.operatedId.eq(fl.getId())));

    db.execute(Select.from(VU.pilotOnFlight).join(VU.pilot).on(VU.pilotOnFlight.pilotId.eq(VU.pilot.id))
        .where(VU.pilotOnFlight.operatedId.eq(fl.getId()))).getPilots()
        .forEach(System.out::println);

    System.out.println("\nIf everything went right, there should now be two pilots assigned for this flight " +
        "(it's a 5 hour flight on an A35K, so there should only be two pilots.)");

    waitForInput();

    System.out.println("\n\nCrew can be assigned manually...\n");
    insert = Insert.into(VU.crewOnFlight).attributes(VU.crewOnFlight.crewId, VU.crewOnFlight.operatedId)
        .values(1, fl.getId());

    System.out.println(insert);
    inserted = db.execute(insert);
    System.out.println("Inserted " + inserted + " values");

    waitForInput();

    System.out.println("\n\nFunction 7.2 - printing pilots and crew\n\n");

    System.out.println("Pilots have already been printed when testing the AssignPilots function.");
    System.out.println("Flight attendants can be printed in much the same way.\n");

    db.execute(Select.from(VU.crewOnFlight).join(VU.crew).on(VU.crewOnFlight.crewId.eq(VU.crew.id))
        .where(VU.crewOnFlight.operatedId.eq(fl.getId()))).getCrew()
        .forEach(System.out::println);

    System.out.println("\n\nFunction 7.3 - Replacing crew\n\n");
    System.out.println("Replacing FAs or pilots just means executing an update on either the pilot_on_flight or " +
        "crew_on_flight table.");

    update = Update.table(VU.crewOnFlight).set(VU.crewOnFlight.crewId, 2)
        .where(VU.crewOnFlight.crewId.eq(1).and(VU.crewOnFlight.operatedId.eq(fl.getId())));
    System.out.println("\nQuery: " + update + "\n");
    updated = db.execute(update);
    System.out.println("Updated " + updated + " rows.\n");
    System.out.println("Printing...\n");

    db.execute(Select.from(VU.crewOnFlight).join(VU.crew).on(VU.crewOnFlight.crewId.eq(VU.crew.id))
        .where(VU.crewOnFlight.operatedId.eq(fl.getId()))).getCrew()
        .forEach(System.out::println);

    waitForInput();

    System.out.println("\n\nFunction 7.4 - Removing crew from flights\n\n");

    delete = Delete.from(VU.pilotOnFlight).where(VU.pilotOnFlight.operatedId.eq(fl.getId()));
    System.out.println("Query: " + delete + "\n");
    deleted = db.execute(delete);
    System.out.println("Deleted " + deleted + " rows.");
    System.out.println("Pilots on operated flight number \" +  fl.getId() + \" (there should be none): " );

    db.execute(Select.from(VU.pilotOnFlight).join(VU.pilot).on(VU.pilotOnFlight.pilotId.eq(VU.pilot.id))
        .where(VU.pilotOnFlight.operatedId.eq(fl.getId()))).getPilots()
        .forEach(System.out::println);

    delete = Delete.from(VU.crewOnFlight).where(VU.crewOnFlight.operatedId.eq(fl.getId()));
    System.out.println("Query: " + delete + "\n");
    deleted = db.execute(delete);
    System.out.println("Deleted " + deleted + " rows.");
    System.out.println("Crew on operated flight number " +  fl.getId() + " (there should be none): ");

    db.execute(Select.from(VU.pilotOnFlight).join(VU.pilot).on(VU.pilotOnFlight.pilotId.eq(VU.pilot.id))
        .where(VU.pilotOnFlight.operatedId.eq(fl.getId()))).getPilots()
        .forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting DB to original state...");
    db.execute(Delete.from(VU.flightTicket).where(VU.flightTicket.operatedId.gte(fl.getId())));
    db.execute(Delete.from(VU.crewOnFlight).where(VU.crewOnFlight.operatedId.gte(fl.getId())));
    db.execute(Delete.from(VU.pilotOnFlight).where(VU.pilotOnFlight.operatedId.gte(fl.getId())));

    db.execute(Delete.from(VU.operatedFlight).where(VU.operatedFlight.id.gte(fl.getId())));

  }

  public static void builderTest() {
    var sel = Select.from(VU.flight).join(VU.route).on(VU.route.id.eq(VU.flight.routeId))
        .where(VU.route.origin.eq("LKXB").and(VU.route.etopsRequirement.eq(0)));

    System.out.println(sel.build());
    System.out.println("\n");
    System.out.println(sel);

    System.out.println("\n");


    var update = Update.table(VU.flight)
        .set(VU.flight.departureTime, new LocalTime(4, 15, 0))
        .set(VU.flight.aircraftModelDesignator, "CONC")
        .set(VU.flight.id, "VU0666")
        .where(VU.flight.id.eq("VU1234"));

    System.out.println(update.build());
    System.out.println("\n");
    System.out.println(update);

    System.out.println("\n");


    var insert = Insert.into(VU.airport)
        .attributes(VU.airport.icao, VU.airport.iata, VU.airport.name)
        .values("LKMT", "OSR", "Letiště Leoše Janáčka, Ostrava");

    System.out.println(insert.build());
    System.out.println("\n");
    System.out.println(insert);

    System.out.println("\n");

    var delete = Delete.from(VU.flight)
        .where(VU.flight.aircraftModelDesignator.eq("CONC")
            .and(VU.flight.departureTime.eq(new LocalTime(9, 0, 0))));

    System.out.println(delete.build());
    System.out.println("\n");
    System.out.println(delete);

    System.out.println("\n");
  }

  public static void routeManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 4.1 through 4.9 - Fleet management\n");

    System.out.println("Function 4.1 - adding routes\n\n");

    System.out.println("First, we will add two airports (ICN, NRT) to the DB and then create a route between those two airports\n");

    var seoul = new Airport("RKSI", "ICN", "Incheon International Airport");

    // Either way is fine
    db.execute(Insert.into(VU.airport).row(seoul));
    db.execute(Insert.into(VU.airport).values("RJAA", "NRT", "Narita International Airport"));
    waitForInput();

    System.out.println("Now, we will create a route between those two airports.\n");

    var insert = Insert.into(VU.route)
            .attributes(VU.route.distance, VU.route.etopsRequirement, VU.route.origin, VU.route.destination)
            .values(500, 0, "RKSI", "RJAA");

    System.out.println("Query: " + insert + "\n");

    var inserted = db.execute(insert);
    System.out.println("Inserted " + inserted + " values");
    waitForInput();

    System.out.println("Function 4.2 - printing routes. \n");

    System.out.println("Print three routes...\n");

    var select = Select.from(VU.route);
    var routes = db.execute(select).getRoutes();
    routes.stream().limit(3).forEach(System.out::println);

    System.out.println("\n\nFunction 4.3 - editing routes");
    var update = Update.table(VU.route)
        .set(VU.route.distance, 750)
        .set(VU.route.etopsRequirement, 60)
        .where(VU.route.origin.eq("RKSI"));
    System.out.println("\nQuery: " + update);
    var updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " values\n");

    System.out.println("\nFetching updated row from db...\n");

    db.execute(Select.from(VU.route).where(VU.route.origin.eq("RKSI"))).getRoutes().forEach(System.out::println);
    waitForInput();

    System.out.println("\nFunction 4.4 - adding flights\n\n");

    System.out.println("\nCreating flight on the newly created route...\n");

    var routeId = db.execute(Select.from(VU.route).where(VU.route.origin.eq("RKSI"))).getRoutes().get(0).getId();
    insert = Insert.into(VU.flight)
        .values("VU0999", new LocalTime(12, 0),
            new LocalTime(12, 0), "BCS3", routeId);

    System.out.println("Query: " + insert);
    inserted = db.execute(insert);
    System.out.println("\nInserted " + inserted + " rows\n\n");

    waitForInput();

    System.out.println("\nFunction 4.5 - printing flights\n\n");

    select = Select.from(VU.flight).join(VU.route).on(VU.route.id.eq(VU.flight.routeId))
            .where(VU.flight.aircraftModelDesignator.eq("CONC"));

    System.out.println("Query: " + select + "\n");
    db.execute(select).getFlights().forEach(System.out::println);

    System.out.println("\nFunction 4.5 - passenger counts per class on flight\n\n");

    var res = (PaxPerClassQuery)db.execute(new PaxPerClassQuery("VU0141"));
    System.out.println("Total number of economy passengers across all flights VU141: " + res.getEconomyPassengers());
    System.out.println("Total number of business passengers across all flights VU141: " + res.getBusinessPassengers());
    System.out.println("Total number of first passengers across all flights VU141: " + res.getFirstPassengers());

    waitForInput();

    System.out.println("\nFunction 4.6 - editing flights\n\n");

    update = Update.table(VU.flight)
            .set(VU.flight.aircraftModelDesignator, "CONC")
            .where(VU.flight.id.eq("VU0999"));

    System.out.println("Query: " + update + "\n");
    updated = db.execute(update);
    System.out.println("Updated " + updated + " rows\n.");

    db.execute(Select.from(VU.flight).where(VU.flight.id.eq("VU0999"))).getFlights().forEach(System.out::println);

    waitForInput();
    System.out.println("\nFunction 4.7 - removing flights\n\n");
    var delete = Delete.from(VU.flight).where(VU.flight.id.eq("VU0999"));
    System.out.println("Query: " + delete);
    var deleted = db.execute(delete);
    System.out.println("Deleted " + deleted + " values.\n");

    waitForInput();

    System.out.println("\nFunction 4.8 - searching by destination\n\n");
    System.out.println("Searching for flights between Kobeřice and Dubai. Should yield multiple single segment flights\n");

    var flights = db.execute(new FlightSearchQuery("LKXB", "OMDB"));
    System.out.println(flights);

    System.out.println("\nSearching for flights between Singapore and Dubai. Should yield multiple two segment flights\n");
    System.out.println("First flight: SIN-KXB, Second flight: KXB-DXB (same flights as above)\n");

    flights = db.execute(new FlightSearchQuery("WSSS", "OMDB"));
    System.out.println(flights);

    System.out.println("\nFunction 4.8 - searching by Aircraft Type\n\n");
    System.out.println("Let's say we want to fly an A320 and we don't care whether it's and A320 or an A321.");
    System.out.println("Output is limited to 4 flights at most so as not to clutter stdout too much.\n\n");

    select = Select.from(VU.flight)
            .join(VU.aircraftModel)
            .on(VU.aircraftModel.designator.eq(VU.flight.aircraftModelDesignator))
            .where(VU.aircraftModel.family.eq("A320"));

    System.out.println("Query: " + select + "\n");

    var a320flights = db.execute(select).getFlights();

    a320flights.stream().limit(4).forEach(System.out::println);

    waitForInput();

    System.out.println("\n\nResetting db into original state...");
    db.execute(Delete.from(VU.route).where(VU.route.origin.eq("RKSI")));
    db.execute(Delete.from(VU.airport).where(VU.airport.iata.eq("ICN").or(VU.airport.iata.eq("NRT"))));

  }

  public static void fleetManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 2.1 through 2.7 - Fleet management\n");
    waitForInput();

    var insert = Insert.into(VU.aircraftModel)
            .attributes(VU.aircraftModel.designator, VU.aircraftModel.manufacturer, VU.aircraftModel.family,
                        VU.aircraftModel.fullType, VU.aircraftModel.etopsCertified, VU.aircraftModel.etopsRating,
                        VU.aircraftModel.rangeNmi, VU.aircraftModel.mtow)
            .values("B748", "Boeing", "B747", "Boeing 747-8i", "NA", 0, 7730, 987000);

    System.out.println("\nFunction 2.1 - inserting aircraft model into DB");

    System.out.println("\nQuery: " + insert);
    var res = db.execute(insert);
    System.out.println("\nInserted " + res + " values");

    System.out.println("\nFunction 2.2 - fetching aircraft models from DB");

    var select = Select.from(VU.aircraftModel);

    System.out.println("\nQuery: " + select + "\n");
    var acModels = db.execute(select).getAircraftModels();
    acModels.forEach(System.out::println);

    System.out.println("\nFunction 2.3 - Updating aircraft models");

    var update = Update.table(VU.aircraftModel).set(VU.aircraftModel.fullType, "Kotlin Scala Groovy Whatever")
            .where(VU.aircraftModel.designator.eq("B748"));

    System.out.println("\nQuery: " + update + "\n");
    var updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " values\n");

    System.out.println("Querying db for updated model.");
    select = Select.from(VU.aircraftModel).where(VU.aircraftModel.designator.eq("B748"));
    System.out.println("\nQuery: " + select + "\n");
    db.execute(select).getAircraftModels().forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting DB by deleting B748 model...");
    db.execute(Delete.from(VU.aircraftModel).where(VU.aircraftModel.designator.eq("B748")));

    System.out.println("\nFunction 2.4 - Adding aircraft to fleet");

    insert = Insert.into(VU.aircraft)
            .values("OK-VSB", "Rolls Royce Trent-1000", 250, 70, 0,
                new LocalDate(2020, 4, 15), "A35K");
    System.out.println("\nQuery: " + insert);
    res = db.execute(insert);
    System.out.println("\nInserted " + res + " values");

    System.out.println("\nFunction 2.5 - Fetching fleet info from DB");

    System.out.println("\n\nOnly print aircraft from the A320 family so as to avoid printing way too much irrelevant info.");

    select = Select.from(VU.aircraft)
            .join(VU.aircraftModel).on(VU.aircraftModel.designator.eq(VU.aircraft.modelDesignator))
            .where(VU.aircraftModel.family.eq("A320"));

    System.out.println("\nQuery: " + select + "\n\n");
    db.execute(select).getAircraft().forEach(System.out::println);

    System.out.println("\nFunction 2.6 - Updating aircraft\n");
    waitForInput();
    update = Update.table(VU.aircraft)
            .set(VU.aircraft.businessSeats, 120)
            .set(VU.aircraft.economySeats, 180)
            .set(VU.aircraft.firstSeats, 9)
            .set(VU.aircraft.lastCheck, new LocalDate(2020, 5, 17))
            .where(VU.aircraft.identifier.eq("OK-VSB"));

    System.out.println("\nQuery: " + update + "\n");
    updated = db.execute(update);
    System.out.println("Updated " + updated + " rows\n");

    select = Select.from(VU.aircraft)
            .where(VU.aircraft.identifier.eq("OK-VSB"));
    db.execute(select).getAircraft().forEach(System.out::println);

    System.out.println("\nFunction 2.7 - Removing aircraft\n");

    System.out.println("To demonstrate, we will first assign the aircraft onto a flight...\n");

    update = Update.table(VU.operatedFlight).set(VU.operatedFlight.aircraftIdentifier, "OK-VSB")
            .where(VU.operatedFlight.id.eq(25));
    db.execute(update);

    select = Select.from(VU.operatedFlight).where(VU.operatedFlight.aircraftIdentifier.eq("OK-VSB"));
    db.execute(select).getOperatedFlights().forEach(System.out::println);

    System.out.println("\nNow we delete the aircraft from DB.\n");

    var delete = Delete.from(VU.aircraft).where(VU.aircraft.identifier.eq("OK-VSB"));

    System.out.println("Query: " + delete);
    var deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " rows\n");

    System.out.println("ID of aircraft assigned for flight 25 should now be 'null'\n");

    select = Select.from(VU.operatedFlight).where(VU.operatedFlight.id.eq(25));
    db.execute(select).getOperatedFlights().forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting DB to original state...");
    update = Update.table(VU.operatedFlight).set(VU.operatedFlight.aircraftIdentifier, "OK-XWB")
            .where(VU.operatedFlight.id.eq(25));
    db.execute(update);


  }

  public static void airportSearchTest(Database db) throws SQLException {

    System.out.println("\nFunction 3.1 - Airport retrieval\n");
    waitForInput();

    var select = Select.from(VU.airport).where(VU.airport.iata.eq("DXB"));
    System.out.println("\n\nQuery: " + select);
    System.out.println("\nQuery should yield Dubai International Airport\n");

    db.execute(select).getAirports().forEach(System.out::println);

    select = Select.from(VU.airport).where(VU.airport.icao.eq("EDDF"));
    System.out.println("Query: " + select);
    System.out.println("\nQuery should yield Frankfurt Airport\n");

    db.execute(select).getAirports().forEach(System.out::println);

    select = Select.from(VU.airport).where(VU.airport.name.ilike("%london%"));
    System.out.println("Query: " + select);
    System.out.println("\nQuery should return all airports with London in their name\n");

    db.execute(select).getAirports().forEach(System.out::println);

    waitForInput();

  }

  public static void passengerTest(Database db) throws SQLException {

    System.out.println("Functions 1.1 through 1.4 and 8.1 through 8.4 - Passenger account management");
    waitForInput();

    System.out.println("\n\nFunction 1.1: Creating a passenger account");
    var insert = Insert
            .into(VU.passenger)
            .attributes(VU.passenger.firstName, VU.passenger.lastName, VU.passenger.preferredMeal, VU.passenger.preferredSeat)
            .values("Filip", "Peterek", "vegetarian", "window");

    System.out.println("\n\nQuery: " + insert);

    var inserted = db.execute(insert);

    System.out.println("\nInserted " + inserted + " values.");

    var select = Select.from(VU.passenger).where(VU.passenger.lastName.eq("Peterek"));
    var res = db.execute(select);
    int peterekId = res.getPassengers().get(0).getId();

    System.out.println("\n\nFunction 8.1: Booking a flight");
    System.out.println("\n\nInserting myself onto three flights...");
    insert = Insert.into(VU.flightTicket).attributes(VU.flightTicket.meal, VU.flightTicket.seat, VU.flightTicket.travelClass,
        VU.flightTicket.baggageAllowance, VU.flightTicket.operatedId, VU.flightTicket.passengerId)
            .values("vegetarian", "13A", "business",  32, 1, peterekId);
    System.out.println("\n\nQuery: " + insert);
    db.execute(insert);
    insert = Insert.into(VU.flightTicket).attributes(VU.flightTicket.meal, VU.flightTicket.seat, VU.flightTicket.travelClass,
        VU.flightTicket.baggageAllowance, VU.flightTicket.operatedId, VU.flightTicket.passengerId)
            .values("vegetarian", "10C", "business", 32, 13, peterekId);
    db.execute(insert);
    insert = Insert.into(VU.flightTicket).attributes(VU.flightTicket.meal, VU.flightTicket.seat, VU.flightTicket.travelClass,
        VU.flightTicket.baggageAllowance, VU.flightTicket.operatedId, VU.flightTicket.passengerId)
            .values("vegetarian", "3A", "business",  32, 17, peterekId);
    db.execute(insert);

    System.out.println("\n\nFunction 1.2 - fetching info about myself");

    select = Select.from(VU.flightTicket)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
            .where(VU.passenger.lastName.eq("Peterek"));
    res = db.execute(select);
    var peterek = res.getPassengers().get(0);
    System.out.println(peterek);

    System.out.println("\n\nFunction 8.2 - Listing my own tickets");

    var flights = peterek.getFlightTickets();
    flights.forEach(System.out::println);

    System.out.println("\n\nFunction 1.3 and 8.3 - Updating personal information and tickets");
    waitForInput();

    peterek.setPreferredMeal("vegan");
    peterek.setPreferredSeat(SeatType.Aisle);

    var update = Update.table(VU.passenger).row(peterek);
    System.out.println("\n\nQuery: " + update);
    var updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " rows");

    update = Update.table(VU.flightTicket).set(VU.flightTicket.baggageAllowance, 64)
            .where(VU.flightTicket.passengerId.eq(peterekId));
    System.out.println("\n\nQuery: " + insert);
    updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " rows");

    System.out.println("\nFetching updated data...");

    select = Select.from(VU.flightTicket)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
            .where(VU.passenger.lastName.eq("Peterek"));
    res = db.execute(select);
    peterek = res.getPassengers().get(0);
    System.out.println(peterek);

    System.out.println("\n");

    flights = peterek.getFlightTickets();
    flights.forEach(System.out::println);

    System.out.println("Functions 1.4 and 8.4 - Cancelling flights and deleting my account");
    waitForInput();

    var delete = Delete.from(VU.flightTicket)
            .where(
                    VU.flightTicket.passengerId.eq(peterekId).and(
                            VU.flightTicket.operatedId.eq(13))
            );

    System.out.println("\n\nQuery: " + delete);
    var deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " rows");

    System.out.println("\nFetching my own flights again: ");
    select = Select.from(VU.flightTicket)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.flightTicket.passengerId))
            .where(VU.passenger.lastName.eq("Peterek"));
    res = db.execute(select);
    flights = res.getFlightTickets();
    flights.forEach(System.out::println);

    delete = Delete.from(VU.passenger).where(VU.passenger.lastName.eq("Peterek"));
    System.out.println("\n\nQuery: " + delete);
    deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " rows");

    System.out.println("\n\nThere should now be two flights with a dummy...");
    select = Select.from(VU.flightTicket).where(VU.flightTicket.passengerId.eq(0));
    res = db.execute(select);
    flights = res.getFlightTickets();
    flights.forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting database before moving onto another test...");

    var del = Delete.from(VU.flightTicket).where(VU.flightTicket.passengerId.eq(0));
    db.execute(del);

  }

}
