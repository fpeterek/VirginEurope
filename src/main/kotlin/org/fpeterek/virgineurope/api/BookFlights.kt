package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.common.TravelClass
import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.entities.FlightTicket
import org.fpeterek.virgineurope.orm.entities.Passenger
import java.lang.Exception

object BookFlights {

    private fun allowanceByClass(cls: TravelClass) = when (cls) {
        TravelClass.Economy -> 32
        TravelClass.Business -> 64
        TravelClass.First -> 92
    }

    private fun fetchFlight(fl: Int) = DB.execute(
        SELECT FROM VU.operatedFlight WHERE (VU.operatedFlight.id EQ fl)
    ).operatedFlights.firstOrNull()

    private fun fetchPax(pax: Int) = DB.execute(
        SELECT FROM VU.passenger WHERE (VU.passenger.id EQ pax)
    ).passengers.firstOrNull()

    private fun createTicket(pax: Passenger, cls: TravelClass, fl: Int) =
        FlightTicket(0, pax.preferredMeal, "1A", cls, allowanceByClass(cls),
        fl, null, pax.id, pax)

    private fun book(pax: Passenger, cls: TravelClass, fl: Int) = DB.execute(
        INSERT INTO (VU.flightTicket) ROW createTicket(pax, cls, fl)
    )

    fun book(passengerId: String, travelClass: String, flight1: String, flight2: String): String {

        val paxId = passengerId.toIntOrNull()
            ?: return """{"error": "Passenger not supplied or invalid"}"""
        val fl1 = flight1.toIntOrNull()
            ?: return """{"error": "Argument flight1 not supplied or invalid"}"""
        val fl2 = flight2.toIntOrNull()

        fetchFlight(fl1) ?: return """{"error": "Flight doesn't exist."}"""
        if (fl2 != null) {
            fetchFlight(fl2) ?: return """{"error": "Flight doesn't exist."}"""
        }

        val pax = fetchPax(paxId) ?: return """{"error": "Passenger doesn't exist."}"""

        val cls = try {
            TravelClass.fromString(travelClass)
        } catch (e: Exception) {
            return """{"error": "Travel class not supplied or invalid"}"""
        }

        book(pax, cls, fl1)
        if (fl2 != null) {
            book(pax, cls, fl2)
        }

        return """{"success": "success"}"""

    }

}
