package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.orm.Database;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Select;
import org.fpeterek.virgineurope.orm.sql.Update;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello, World!");

    var select = Select
            .from(VU.operatedFlight)
            .join(VU.aircraft)
              .on(VU.operatedFlight.aircraftIdentifier
                      .eq(VU.aircraft.identifier))
            .join(VU.aircraftModel)
              .on(VU.aircraft.modelDesignator
                      .eq(VU.aircraftModel.designator))
            .where(VU.aircraft.modelDesignator.eq("CONC"));

    System.out.println(select);

    select = Select
            .from(VU.route)
            .join(VU.airport.as("a1")).on(VU.route.origin.eq(VU.airport.as("a1").icao))
            .join(VU.airport.as("a2")).on(VU.route.destination.eq(VU.airport.as("a2").icao));

    System.out.println(select);



    try {
      Database db = new Database();
      var sel = Select.from(VU.airport);
      var airports = db.execute(sel).getAirports();
      System.out.println(sel);
      System.out.println("Printing airports: ");
      airports.stream().limit(3).forEach(System.out::println);

      sel = Select
              .from(VU.route)
              .join(VU.airport.as("a1")).on(VU.route.origin.eq(VU.airport.as("a1").icao))
              .join(VU.airport.as("a2")).on(VU.route.destination.eq(VU.airport.as("a2").icao));

      var res = db.execute(sel);
      var routes = res.getRoutes();

      routes.stream().limit(3).forEach(System.out::println);

      sel = Select
              .from(VU.passenger)
              .join(VU.passengerOnFlight).on(VU.passengerOnFlight.passengerId.eq(VU.passenger.id))
              .where(VU.passenger.id.eq("3"));

      System.out.println(sel);

      res = db.execute(sel);
      var passengers = res.getPassengers();
      passengers.forEach(pax -> {
        System.out.println(pax.fullName() + " flights: " + pax.getFlightTickets().size());
      });

      var insert = Insert.into(VU.passenger)
              .attributes(VU.passenger.firstName, VU.passenger.lastName, VU.passenger.preferredSeat, VU.passenger.preferredMeal)
              .values("Dummy", "Dummy", "window", "vegan");
      System.out.println(insert);

      var inret = db.execute(insert);
      System.out.println("Inserted " + inret + " values");

      var fnameCond = VU.passenger.firstName.eq("Dummy");
      var lnameCond = VU.passenger.lastName.eq("Dummy");
      var nameCond = fnameCond.and(lnameCond);

      var delete = Delete.from(VU.passenger).where(nameCond);
      System.out.println(delete);
      var delret = db.execute(delete);
      System.out.println("Deleted " + delret + " values");

      delete = Delete.from(VU.passenger).where(VU.passenger.id.eq("120"));
      db.execute(delete);

      insert = Insert.into(VU.passenger)
              .values("120", "Dummy", "Dummaster", "kosher", "aisle");
      System.out.println(insert);
      inret = db.execute(insert);
      System.out.println("Inserted " + inret + " values");

      insert = Insert.into(VU.passenger)
              .values("1000", "Work", "Please", "default", "aisle");
      db.execute(insert);

      var update = Update.table(VU.passenger)
              .set(VU.passenger.lastName, "Peterek")
              .set(VU.passenger.firstName, "Filip")
              .set(VU.passenger.preferredSeat, "window")
              .set(VU.passenger.preferredMeal, "vegetarian")
              .where(VU.passenger.id.eq("1000"));
      System.out.println(update);
      var upret = db.execute(update);
      System.out.println("Updated " + upret + " values");


      System.out.println("-------------- Checking functions described by specification --------------");

      sel = Select.from(VU.operatedFlight)
              .join(VU.flight).on(VU.operatedFlight.flightId.eq(VU.flight.id))
              .join(VU.route).on(VU.flight.routeId.eq(VU.route.id))
              .join(VU.aircraftModel).on(VU.aircraftModel.designator.eq(VU.flight.aircraftModelDesignator))
              .where(VU.aircraftModel.family.eq("A350").and(
                      VU.route.origin.eq("LKXB")).and(
                      VU.operatedFlight.date.eq("2019-12-19")
              ));

      System.out.println(sel);

      res = db.execute(sel);
      res.getOperatedFlights().forEach(System.out::println);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

}
