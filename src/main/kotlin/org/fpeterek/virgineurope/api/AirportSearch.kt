package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.entities.Airport

object AirportSearch {

    private fun likeSearchIcao(query: String): List<Airport> =
        DB.execute(SELECT FROM VU.airport WHERE (VU.airport.icao LIKE "%$query%")).airports

    private fun eqSearchIcao(query: String): List<Airport> =
        DB.execute(SELECT FROM VU.airport WHERE (VU.airport.icao EQ query)).airports

    private fun likeSearchIata(query: String): List<Airport> =
        DB.execute(SELECT FROM VU.airport WHERE (VU.airport.iata LIKE "%$query%")).airports

    private fun eqSearchIata(query: String): List<Airport> =
        DB.execute(SELECT FROM VU.airport WHERE (VU.airport.iata EQ query)).airports

    private fun searchByName(query: String): List<Airport> =
        DB.execute(SELECT FROM VU.airport WHERE (VU.airport.name ILIKE "%$query%")).airports

    private fun searchByIcao(query: String) = when {
        query.length < 4 -> likeSearchIcao(query)
        isValidIcao(query) -> eqSearchIcao(query)
        else -> listOf()
    }

    private fun searchByIata(query: String) = when {
        query.length < 3 -> likeSearchIata(query)
        isValidIata(query) -> eqSearchIata(query)
        else -> listOf()
    }

    private fun combineSearches(query: String) = listOf(
        searchByIata(query.toUpperCase()),
        searchByIcao(query.toUpperCase()),
        searchByName(query.toUpperCase()))
        .asSequence()
        .flatten()
        .map { "\"${it.name} (${it.iata}, ${it.icao})\"" }
        .toSet()
        .take(10)
        .joinToString(separator = ",", prefix = "[", postfix = "]")

    fun searchAirports(query: String) = combineSearches(query)

}
