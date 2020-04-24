package org.fpeterek.virgineurope.orm.sql;

import org.fpeterek.virgineurope.util.UtilKt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class Util {

  /* Postgres will raise an exception if I tried setString on a numeric value  */
  /* Also Java doesn't really offer a reasonable way to check whether a string */
  /* is a number and I refuse to use exceptions as control flow                */
  /* I do realize this is kinda ridiculous, but I believe it should be ok      */
  /* for my use case                                                           */
  /* It could be done in a better way in other languages, and with some effort */
  /* probably in Java, too, but I don't have that much time                    */
  static void addToStatement(int index, PreparedStatement stmt, String value) throws SQLException {
    if (UtilKt.isNumeric(value)) {
      stmt.setInt(index, Integer.parseInt(value));
    } else if (UtilKt.isDate(value)) {
      stmt.setDate(index, Date.valueOf(value));
    } else if (UtilKt.isTime(value)) {
      stmt.setTime(index, Time.valueOf(value));
    } else {
      stmt.setString(index, value);
    }
  }

}
