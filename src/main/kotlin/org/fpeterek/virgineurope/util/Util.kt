package org.fpeterek.virgineurope.util

// This literally is the easiest way to do this
// Java cannot stream over Strings
fun isNumeric(str: String?) = !str.isNullOrEmpty() && str.all { it.isDigit() }

// Incorrect checks, but I don't care, for now it's good enough
// Might fix later

fun isDate(str: String?) =
    !str.isNullOrEmpty() && str matches "[0-9]{4}-[0-1][0-9]-[0-3][0-9]".toRegex()

fun isTime(str: String?) =
    !str.isNullOrEmpty() && str matches "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]".toRegex()
