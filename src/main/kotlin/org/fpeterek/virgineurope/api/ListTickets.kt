package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.entities.FlightTicket
import org.fpeterek.virgineurope.orm.tables.AirportTable
import org.joda.time.LocalDate
import org.joda.time.LocalTime

object ListTickets {

    private val destTable = (VU.airport AS "dest") as AirportTable
    private val origTable = (VU.airport AS "orig") as AirportTable

    private fun selectQuery(pax: Int) = SELECT FROM VU.flightTicket JOIN
            VU.operatedFlight ON (VU.operatedFlight.id EQ VU.flightTicket.operatedId) JOIN
            VU.flight ON (VU.operatedFlight.flightId EQ VU.flight.id) JOIN
            VU.route ON (VU.route.id EQ VU.flight.routeId) JOIN
            destTable ON (VU.route.destination EQ destTable.icao) JOIN
            origTable ON (VU.route.origin EQ origTable.icao)

    private fun queryAllTickets(pax: Int): List<FlightTicket> = DB.execute(
             selectQuery(pax) WHERE (VU.flightTicket.passengerId EQ pax)
    ).flightTickets

    private fun queryByDate(pax: Int, date: LocalDate): List<FlightTicket> = DB.execute(
            selectQuery(pax) WHERE
                    ((VU.flightTicket.passengerId EQ pax) AND (VU.operatedFlight.date GTE date))
    ).flightTickets

    private fun format(time: LocalTime) = time.toString("HH:mm:ss")

    private fun formatTicket(ticket: FlightTicket) =
            """{
                |"id": ${ticket.ticketId},
                |"flight": "${ticket.operatedFlight.flightId}",
                |"date": "${ticket.operatedFlight.date}",
                |"class": "${ticket.travelClass.dbValue()}",
                |"meal": "${ticket.meal}",
                |"allowance": ${ticket.baggageAllowance},
                |"seat": "${ticket.seat}",
                |"destination": "${ticket.operatedFlight.flight.route.destination.iata}",
                |"origin": "${ticket.operatedFlight.flight.route.origin.iata}",
                |"departure": "${format(ticket.operatedFlight.flight.departureTime)}",
                |"equipment": "${ticket.operatedFlight.flight.modelDesignator}"
            |}""".trimMargin().replace("\n", "")

    private fun queryTickets(id: Int, all: Boolean) = if (all) {
        queryAllTickets(id)
    } else {
        queryByDate(id, LocalDate.now())
    }

    private fun formatJson(ticketList: String) = """{"tickets": $ticketList}"""

    fun listTickets(pax: String, allTickets: String): String {

        val id = pax.toIntOrNull() ?: return """{"error": "Invalid ID"}"""
        val all = allTickets == "1" || allTickets.toLowerCase() == "true"

        val tickets = queryTickets(id, all).joinToString(separator=",", prefix="[", postfix="]") {
            formatTicket(it)
        }

        return formatJson(tickets)
    }

}
