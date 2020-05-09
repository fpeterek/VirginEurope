package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.common.TravelClass
import org.fpeterek.virgineurope.orm.BooleanExpr
import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.entities.Flight
import org.fpeterek.virgineurope.orm.entities.OperatedFlight
import org.fpeterek.virgineurope.orm.sql.custom.FlightSearchQuery
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.yaml.snakeyaml.Yaml

object FlightSearch {

    private data class Query(val orig: String, val dest: String, val date: LocalDate, val cls: TravelClass) {

        companion object {
            fun fromYaml(string: String): Query {

                val obj = Yaml().load<Map<String, String>>(string)

                val orig = obj["orig"] ?: ""
                val dest = obj["dest"] ?: ""
                val date = LocalDate.parse(obj["date"] ?: "", DateTimeFormat.forPattern("dd/MM/yyyy"))
                val cls = TravelClass.fromString(obj["cls"] ?: "")

                return Query(orig, dest, date, cls)
            }
        }

    }

    private fun composeCondition(flNumbers: List<String>, it: Int = 0): BooleanExpr =
        if (it == flNumbers.lastIndex) {
            VU.operatedFlight.flightId EQ flNumbers[it]
        } else {
            VU.operatedFlight.flightId EQ flNumbers[it] OR composeCondition(flNumbers, it+1)
        }

    private fun searchSegment(segment: List<String>, date: LocalDate) =
        DB.execute(
            SELECT FROM VU.operatedFlight WHERE (composeCondition(segment) AND (VU.operatedFlight.date GTE date))
        ).operatedFlights.sortedBy { it.date }.take(10)

    private fun findFlightNumbers(query: Query) =
        DB.execute(FlightSearchQuery(query.orig, query.dest)) as FlightSearchQuery

    // Calculate price based on flight distance (date, number of seats left, etc... aren't taken into account)
    private fun priceFlight(fl: Flight) = if (fl.route.distance / 10 < 100) {
        100
    } else {
        (fl.route.distance / 10) / 10 * 10 // Round the number at least
    }

    private fun priceFlights(flightNumbers: List<String>): Map<String, Int> {

        val map = mutableMapOf<String, Int>()

        val query = SELECT FROM VU.flight JOIN VU.route ON (VU.flight.routeId EQ VU.route.id) WHERE composeCondition(flightNumbers)

        DB.execute(query).flights.forEach {
            map[it.flightId] = priceFlight(it)
        }

        return map
    }

    private fun zipFlights(first: List<OperatedFlight>, second: List<OperatedFlight>) = first.zip(second)


    private fun searchFlights(json: String): String {

        val query = Query.fromYaml(json)
        val flNumbers = findFlightNumbers(query)
        val prices = priceFlights(flNumbers.firstFlights.toList() + flNumbers.secondFlights.toList())

        if (flNumbers.isSingleSegment) {
            searchSegment(flNumbers.firstFlights, query.date)
        }

        return ""
    }

    fun search(json: String) = try {
        searchFlights(json)
    } catch (e: Exception) {
        """{"error":"invalid_query"}"""
    }


}

