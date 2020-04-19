package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.orm.Database;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Select;
import org.fpeterek.virgineurope.orm.sql.Update;

import java.sql.SQLException;

import static kotlin.io.ConsoleKt.readLine;

public class Main {

  public static void waitForInput() {
    System.out.println("Check the database manually or continue execution by pressing enter...");
    readLine();
  }

  public static void main(String[] args) {

    try {
      Database db = new Database();
      passengerTest(db);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

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
    System.out.println("\n\nQuery: " + insert);
    insert = Insert.into(VU.passengerOnFlight)
            .values("vegetarian", "13A", "business",  "32", "1", String.valueOf(peterekId));
    db.execute(insert);
    insert = Insert.into(VU.passengerOnFlight)
            .values("vegetarian", "10C", "business",  "32", "13", String.valueOf(peterekId));
    db.execute(insert);
    insert = Insert.into(VU.passengerOnFlight)
            .values("vegetarian", "3A", "business",  "32", "17", String.valueOf(peterekId));
    db.execute(insert);

    System.out.println("\n\nFunction 1.2 - fetching info about myself");

    select = Select.from(VU.passengerOnFlight)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.passengerOnFlight.passengerId))
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

    update = Update.table(VU.passengerOnFlight).set(VU.passengerOnFlight.baggageAllowance, "64")
            .where(VU.passengerOnFlight.passengerId.eq(String.valueOf(peterekId)));
    System.out.println("\n\nQuery: " + insert);
    updated = db.execute(update);
    System.out.println("\nUpdated " + updated + " rows");

    System.out.println("\nFetching updated data...");

    select = Select.from(VU.passengerOnFlight)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.passengerOnFlight.passengerId))
            .where(VU.passenger.lastName.eq("Peterek"));
    res = db.execute(select);
    peterek = res.getPassengers().get(0);
    System.out.println(peterek);

    System.out.println("\n");

    flights = peterek.getFlightTickets();
    flights.forEach(System.out::println);

    System.out.println("Functions 1.4 and 8.4 - Cancelling flights and deleting my account");
    waitForInput();

    var delete = Delete.from(VU.passengerOnFlight)
            .where(
                    VU.passengerOnFlight.passengerId.eq(String.valueOf(peterekId)).and(
                            VU.passengerOnFlight.operatedId.eq("13"))
            );

    System.out.println("\n\nQuery: " + delete);
    var deleted = db.execute(delete);
    System.out.println("\nDeleted " + deleted + " rows");

    System.out.println("\nFetching my own flights again: ");
    select = Select.from(VU.passengerOnFlight)
            .join(VU.passenger).on(VU.passenger.id.eq(VU.passengerOnFlight.passengerId))
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
    select = Select.from(VU.passengerOnFlight).where(VU.passengerOnFlight.passengerId.eq("0"));
    res = db.execute(select);
    flights = res.getFlightTickets();
    flights.forEach(System.out::println);

    waitForInput();
    System.out.println("Resetting database before moving onto another test...");

    var del = Delete.from(VU.passengerOnFlight).where(VU.passengerOnFlight.passengerId.eq("0"));
    db.execute(del);

  }

}
