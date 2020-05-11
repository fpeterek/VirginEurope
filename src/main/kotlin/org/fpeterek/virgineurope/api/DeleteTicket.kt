package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.VU

object DeleteTicket {

    private fun deleteTicket(id: Int) = DB.execute(
        DELETE FROM VU.flightTicket WHERE (VU.flightTicket.ticketId EQ id)
    )

    fun delete(ticket: String): String {
        val id = ticket.toIntOrNull() ?: return """{"error": "Invalid ID"}"""
        deleteTicket(id)
        return """{"success": "success"}"""
    }

}
