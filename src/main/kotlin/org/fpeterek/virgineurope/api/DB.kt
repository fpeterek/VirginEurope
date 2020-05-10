package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.Database

// A singleton instance of Database connection
object DB : Database(
    "jdbc:postgresql://localhost:5432/pet0342",
    "fpeterek",
    "psql")
