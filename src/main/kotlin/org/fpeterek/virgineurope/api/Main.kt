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

fun main() {

    val java = Select.from(VU.flight).join(VU.route).on(VU.flight.routeId.eq(VU.route.id)).where(VU.flight.id.eq("VU0139"))

    val kotlin = SELECT FROM VU.flight JOIN VU.route ON (VU.flight.routeId EQ VU.route.id) WHERE (VU.flight.id EQ "VU0139")


    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("My Example Api", ContentType.Text.Html)
            }
            get("/airports") {
                val query = (this.context.parameters["q"] ?: "").removePrefix("'").removeSuffix("'")
                call.respondText(AirportSearch.searchAirports(query), ContentType.Application.Json)
            }
            post("/find-flights") {
                val body = call.receiveText()
                call.respondText(FlightSearch.search(body), ContentType.Application.Json)
            }
        }
    }.start(wait = true)
}
