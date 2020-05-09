package org.fpeterek.virgineurope.api

fun isValidIcao(query: String) = query matches "[A-Z]{4}".toRegex()
fun isValidIata(query: String) = query matches "[A-Z]{3}".toRegex()
