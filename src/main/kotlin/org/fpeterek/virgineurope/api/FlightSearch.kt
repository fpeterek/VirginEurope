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
                val date = LocalDate.parse(obj["date"] ?: "", DateTimeFormat.forPattern("MM/dd/yyyy"))
                val cls = TravelClass.fromString(obj["cls"] ?: "")

                return Query(orig, dest, date, cls)
            }

            fun fromStrings(orig: String, dest: String, date: String, cls: String) = Query(
                    orig,
                    dest,
                    LocalDate.parse(date, DateTimeFormat.forPattern("MM/dd/yyyy")),
                    TravelClass.fromString(cls)
            )

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
            SELECT FROM VU.operatedFlight JOIN VU.flight ON
                    (VU.flight.id EQ VU.operatedFlight.flightId) JOIN VU.route ON
                    (VU.route.id EQ VU.flight.routeId) WHERE
                    ((VU.operatedFlight.date GTE date) AND composeCondition(segment))
        ).operatedFlights.sortedBy { it.date }.take(15)

    private fun findFlightNumbers(query: Query) =
        DB.execute(FlightSearchQuery(query.orig, query.dest)) as FlightSearchQuery

    // Calculate price based on flight distance (date, number of seats left, etc... aren't taken into account)
    private fun priceFlight(fl: Flight) = if (fl.route.distance / 10 < 100) {
        100
    } else {
        (fl.route.distance / 10) / 10 * 10 // Round the number
    }

    private fun priceFlights(flights: List<Flight>): Map<String, Int> {

        val map = mutableMapOf<String, Int>()

        flights.forEach {
            map[it.flightId] = priceFlight(it)
        }

        return map
    }

    private fun fetchFlightInfo(flightNumbers: List<String>): Pair<List<Flight>, Map<String, Int>> {

        val query = SELECT FROM VU.flight JOIN VU.route ON
                (VU.flight.routeId EQ VU.route.id) JOIN (VU.operatedFlight) ON
                (VU.operatedFlight.flightId EQ VU.flight.id) WHERE composeCondition(flightNumbers)

        val res = DB.execute(query)

        return res.flights to priceFlights(res.flights)
    }

    // Find a flight that either departs on a different day, or on the same day but only
    // after the first flight has arrived
    private fun findFirstSuitable(flight: OperatedFlight, second: List<OperatedFlight>) =
        second.find {
            (it.date > flight.date) ||
                    (it.date == flight.date && it.flight.departureTime > flight.flight.arrivalTime)
        }

    private fun zipFlights(first: List<OperatedFlight>, second: List<OperatedFlight>) =
        first.map { it to findFirstSuitable(it, second) }
            .filter { it.second != null }
            .map { it.first to it.second!! }
            .take(10)


    // Decrease price of two segment flights so our airline can compete with airlines
    // flying direct flights between two destinations
    // Round price and subtract one to make the price look more appealing
    private fun adjustPrice(price: Int, cls: TravelClass, transfer: Boolean = false) = when (cls) {
        TravelClass.Economy  -> (price * 1 * if (transfer) 0.8 else 1.0).toInt() / 10 * 10 - 1
        TravelClass.Business -> (price * 3 * if (transfer) 0.8 else 1.0).toInt() / 10 * 10 - 1
        TravelClass.First    -> (price * 6 * if (transfer) 0.8 else 1.0).toInt() / 10 * 10 - 1
    }

    private fun calcPrice(flightPair: Pair<OperatedFlight, OperatedFlight>, prices: Map<String, Int>, cls: TravelClass)
        = adjustPrice(
        (prices[flightPair.first.flightId] ?: 0) + (prices[flightPair.second.flightId] ?: 0),
            cls, transfer = true
        )

    private fun formatSingleSegment(
        flights: List<OperatedFlight>, prices: Map<String, Int>, query: Query) =

        """{
            |"single_segment": true,
            |"flights": ${flights.joinToString(separator = ", ", prefix = "[", postfix = "]") {
                """
                |{
                    |"date": "${it.date}",
                    |"flight": "${it.flightId}", 
                    |"id": ${it.id}, 
                    |"price": ${adjustPrice(prices[it.flightId] ?: 0, query.cls)}
                |}""".trimMargin()
            }}
        }""".trimMargin()

    private fun formatTwoSegment(
            flights: List<Pair<OperatedFlight, OperatedFlight>>,
            prices: Map<String, Int>,
            query: Query) =
        """{
            |"two_segment": true,
            |"flights": ${flights.joinToString(separator = ", ", prefix = "[", postfix = "]") {
            """
                |{
                    |"first": "${it.first.flightId}", 
                    |"first_id": ${it.first.id},
                    |"first_date": "${it.first.date}",
                    |"second": "${it.second.flightId}",
                    |"second_id": ${it.second.id},
                    |"second_date": "${it.second.date}",
                    |"price": ${calcPrice(it, prices, query.cls)}
                |}""".trimMargin()
        }}
        }""".trimMargin()

    private fun searchFlights(orig: String, dest: String, date: String, cls: String): String {

        val query = Query.fromStrings(orig, dest, date, cls)
        val flNumbers = findFlightNumbers(query)
        val allFlights = flNumbers.firstFlights.toList() + flNumbers.secondFlights.toString()
        val (flightInfo, prices) = fetchFlightInfo(allFlights)

        val seg1 = searchSegment(flNumbers.firstFlights, query.date)

        return if (flNumbers.isSingleSegment) {
            formatSingleSegment(seg1, prices, query)
        } else {
            val seg2 = searchSegment(flNumbers.secondFlights, query.date)
            val zip = zipFlights(seg1, seg2)
            formatTwoSegment(zip, prices, query)
        }
    }

    fun search(orig: String, dest: String, date: String, cls: String) = try {
        searchFlights(orig, dest, date, cls)
    } catch (e: Exception) {
        println(e)
        e.printStackTrace()
        """{"error":"invalid_query"}"""
    }


}

