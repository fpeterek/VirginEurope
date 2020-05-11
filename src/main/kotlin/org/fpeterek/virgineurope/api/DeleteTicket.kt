package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.VU
import org.joda.time.LocalDate
import org.joda.time.LocalTime

object DeleteTicket {

    private fun deleteTicket(id: Int) = DB.execute(
        DELETE FROM VU.flightTicket WHERE (VU.flightTicket.ticketId EQ id)
    )

    private fun canBeDeleted(id: Int): Boolean {
        val ticket = DB.execute(
            SELECT FROM VU.flightTicket JOIN VU.operatedFlight ON
                    (VU.operatedFlight.id EQ VU.flightTicket.operatedId) JOIN
                    VU.flight ON (VU.flight.id EQ VU.operatedFlight.flightId) WHERE
                    (VU.flightTicket.ticketId EQ id)
        ).flightTickets.firstOrNull()

        ticket ?: return true

        val dateNow = LocalDate.now()
        val timeNow = LocalTime.now()
        val ticketDate = ticket.operatedFlight.date
        val ticketTime = ticket.operatedFlight.flight.departureTime

        return ticketDate > dateNow || (ticketDate == dateNow && timeNow < ticketTime)

    }

    fun delete(ticket: String): String {
        val id = ticket.toIntOrNull() ?: return """{"error": "Invalid ID"}"""

        return if (canBeDeleted(id)) {
            deleteTicket(id)
            """{"success": "success"}"""
        } else {
            """{"error": "Old tickets cannot be refunded"}"""
        }
    }

}
