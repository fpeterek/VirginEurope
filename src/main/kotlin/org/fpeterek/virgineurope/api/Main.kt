package org.fpeterek.virgineurope.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.receiveText
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.sql.Select

private fun extractParameter(params: Parameters, key: String) =
    (params[key] ?: "").removePrefix("'").removeSuffix("'")

fun main() {

    val java = Select.from(VU.flight).join(VU.route).on(VU.flight.routeId.eq(VU.route.id)).where(VU.flight.id.eq("VU0139"))

    val kotlin = SELECT FROM VU.flight JOIN VU.route ON (VU.flight.routeId EQ VU.route.id) WHERE (VU.flight.id EQ "VU0139")


    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("My Example Api", ContentType.Text.Html)
            }
            get("/airports") {
                val query = extractParameter(context.parameters, "q")
                call.respondText(AirportSearch.searchAirports(query), ContentType.Application.Json)
            }
            post("/find-flights") {
                val body = call.receiveText()
                call.respondText(FlightSearch.search(body), ContentType.Application.Json)
            }
            get("/book-flights") {
                val fl1 = extractParameter(context.parameters, "fl1")
                val fl2 = extractParameter(context.parameters, "fl2")
                val pax = extractParameter(context.parameters, "pax")
                val cls = extractParameter(context.parameters, "cls")
                call.respondText(BookFlights.book(pax, cls, fl1, fl2), ContentType.Application.Json)
            }
            get("/list-tickets") {
                val pax = extractParameter(context.parameters, "pax")
                val all = extractParameter(context.parameters, "all")
                call.respondText(ListTickets.listTickets(pax, all), ContentType.Application.Json)
            }
            delete("/delete-ticket") {
                val ticketId = extractParameter(context.parameters, "id")
                call.respondText(DeleteTicket.delete(ticketId), ContentType.Application.Json)
            }
        }
    }.start(wait = true)
}
