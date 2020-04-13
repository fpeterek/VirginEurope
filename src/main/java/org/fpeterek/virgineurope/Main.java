package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.orm.Database;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Select;

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

    var delete = Delete.from(VU.passenger).where(VU.passenger.id.eq("120"));
    System.out.println(delete);

    try {
      Database db = new Database();
      var sel = Select.from(VU.airport);
      var airports = db.execute(sel).getAirports();
      System.out.println(sel);
      System.out.println("Printing airports: ");
      airports.stream().limit(3).forEach(System.out::println);

      var delret = db.execute(delete);
      System.out.println("Deleted " + delret + " values");

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

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

}
