package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.orm.Database;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Select;
import org.fpeterek.virgineurope.orm.sql.Update;
import org.fpeterek.virgineurope.orm.sql.custom.FlightSearchQuery;
import org.fpeterek.virgineurope.orm.sql.custom.PaxPerClassQuery;

import java.sql.SQLException;

import static kotlin.io.ConsoleKt.readLine;

public class Main {

  public static void waitForInput() {
    System.out.println("Check the database manually or continue execution by pressing enter...");
    readLine();
  }

  public static void main(String[] args) {

    try {
      var db = new Database();
      passengerTest(db);
      airportSearchTest(db);
      fleetManagementTest(db);
      routeManagementTest(db);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  public static void routeManagementTest(Database db) throws SQLException {

    System.out.println("\nFunctions 4.1 through 4.9 - Fleet management\n");

    System.out.println("Function 4.1 - adding routes\n\n");

    System.out.println("First, we will add two airports (ICN, NRT) to the DB and then create a route between those two airports\n");

    db.execute(Insert.into(VU.airport).values("RKSI", "ICN", "Incheon International Airport"));
    db.execute(Insert.into(VU.airport).values("RJAA", "NRT", "Narita International Airport"));
    waitForInput();

    System.out.println("Now, we will create a route between those two airports.\n");

    var insert = Insert.into(VU.route)
            .attributes(VU.route.distance, VU.route.etopsRequirement, VU.route.origin, VU.route.destination)
            .values("500", "0", "RKSI", "RJAA");

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
    var update = Update.table(VU.route).set(VU.route.distance, "750").set(VU.route.etopsRequirement, "60")
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
    insert = Insert.into(VU.flight).values("VU0999", "12:00:00", "13:00:00", "BCS3", String.valueOf(routeId));

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
            .values("B748", "Boeing", "B747", "Boeing 747-8i", "NA", "0", "7730", "987000");

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
            .values("OK-VSB", "Rolls Royce Trent-1000", "250", "70", "0", "2020-04-15", "A35K");
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
            .set(VU.aircraft.businessSeats, "120")
            .set(VU.aircraft.economySeats, "180")
            .set(VU.aircraft.firstSeats, "9")
            .set(VU.aircraft.lastCheck, "2020-05-17")
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
            .where(VU.operatedFlight.id.eq("25"));
    db.execute(update);

    select = Select.from(VU.operatedFlight).where(VU.operatedFlight.aircraftIdentifier.eq("OK-VSB"));
    db.execute(select).getOperatedFlights().forEach(System.out::println);

    System.out.println("\nNow we delete the aircraft from DB.\n");

    var delete = Delete.from(VU.aircraft).where(VU.aircraft.identifier.eq("OK-VSB"));

    System.out.println("Query: " + delete);
    var deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " rows\n");

    System.out.println("ID of aircraft assigned for flight 25 should now be 'null'\n");

    select = Select.from(VU.operatedFlight).where(VU.operatedFlight.id.eq("25"));
    db.execute(select).getOperatedFlights().forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting DB to original state...");
    update = Update.table(VU.operatedFlight).set(VU.operatedFlight.aircraftIdentifier, "OK-XWB")
            .where(VU.operatedFlight.id.eq("25"));
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

    select = Select.from(VU.airport).where(VU.airport.name.like("%London%"));
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
    insert = Insert.into(VU.flightTicket)
            .values("vegetarian", "13A", "business",  "32", "1", String.valueOf(peterekId));
    System.out.println("\n\nQuery: " + insert);
    db.execute(insert);
    insert = Insert.into(VU.flightTicket)
            .values("vegetarian", "10C", "business",  "32", "13", String.valueOf(peterekId));
    db.execute(insert);
    insert = Insert.into(VU.flightTicket)
            .values("vegetarian", "3A", "business",  "32", "17", String.valueOf(peterekId));
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

    var update = Update.table(VU.passenger).set(VU.passenger.preferredMeal, "vegan").set(VU.passenger.preferredSeat, "aisle");
    System.out.println("\n\nQuery: " + insert);
    var updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " rows");

    update = Update.table(VU.flightTicket).set(VU.flightTicket.baggageAllowance, "64")
            .where(VU.flightTicket.passengerId.eq(String.valueOf(peterekId)));
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
                    VU.flightTicket.passengerId.eq(String.valueOf(peterekId)).and(
                            VU.flightTicket.operatedId.eq("13"))
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

    // I've only now realised this won't work as you could eventually need to put two dummies onto the same flight.
    // which would break the database's integrity.
    // Though I guess we could also just create 600 dummies and say at most there would an entire A380 filled with
    // anonymous people.
    // I should fix this if I find the time to do so.
    System.out.println("\n\nThere should now be two flights with a dummy...");
    select = Select.from(VU.flightTicket).where(VU.flightTicket.passengerId.eq("0"));
    res = db.execute(select);
    flights = res.getFlightTickets();
    flights.forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting database before moving onto another test...");

    var del = Delete.from(VU.flightTicket).where(VU.flightTicket.passengerId.eq("0"));
    db.execute(del);

  }

}
