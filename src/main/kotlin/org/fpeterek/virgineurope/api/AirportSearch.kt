package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.VU
import org.fpeterek.virgineurope.orm.entities.Airport
import org.fpeterek.virgineurope.orm.sql.Select

object AirportSearch {

    private fun likeSearchIcao(query: String): List<Airport> =
        DB.execute(Select.from(VU.airport).where(VU.airport.icao.like("%$query%"))).airports

    private fun eqSearchIcao(query: String): List<Airport> =
        DB.execute(Select.from(VU.airport).where(VU.airport.icao.eq(query))).airports

    private fun likeSearchIata(query: String): List<Airport> =
        DB.execute(Select.from(VU.airport).where(VU.airport.iata.like("%$query%"))).airports

    private fun eqSearchIata(query: String): List<Airport> =
        DB.execute(Select.from(VU.airport).where(VU.airport.iata.eq(query))).airports

    private fun searchByName(query: String): List<Airport> =
        DB.execute(Select.from(VU.airport).where(VU.airport.name.ilike("%$query%"))).airports

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
        .flatten()
        .map { "\"${it.name} (${it.iata}, ${it.icao})\"" }
        .toSet()
        .joinToString(separator = ",", prefix = "[", postfix = "]")

    fun searchAirports(query: String) = combineSearches(query)

}
