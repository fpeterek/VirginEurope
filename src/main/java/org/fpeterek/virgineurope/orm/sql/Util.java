package org.fpeterek.virgineurope.orm.sql;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class Util {

  public static boolean isNullOrBlank(String str) {
    return (str == null || str.isBlank());
  }

  public static boolean isNumeric(String str) {
    return !isNullOrBlank(str) && str.chars().allMatch(Character::isDigit);
  }

  public static boolean isDate(String str) {
    return !isNullOrBlank(str) && str.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]");
  }

  public static boolean isTime(String str) {
    return !isNullOrBlank(str) && str.matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]");
  }

  public static boolean isBool(String str) {
    return !isNullOrBlank(str) && (str.equals("true") || str.equals("false"));
  }

  public static String format(Object o) {
    if (o instanceof Integer) { return o.toString(); }
    if (o instanceof LocalDate) {
      o = ((LocalDate)o).toString("yyyy-MM-dd");
    } else if (o instanceof LocalTime) {
      o = ((LocalTime)o).toString("HH:mm:ss");
    }
    return String.format("'%s'", o);
  }

  /* Postgres will raise an exception if I tried setString on a numeric value  */
  /* Also Java doesn't really offer a reasonable way to check whether a string */
  /* is a number and I refuse to use exceptions as control flow                */
  /* I do realize this is kinda ridiculous, but I believe it should be ok      */
  /* for my use case                                                           */
  /* It could be done in a better way in other languages, and with some effort */
  /* probably in Java, too, but I don't have that much time                    */
  static void addToStatement(int index, PreparedStatement stmt, String value) throws SQLException {
    if (isNumeric(value)) {
      stmt.setInt(index, Integer.parseInt(value));
    } else if (isDate(value)) {
      stmt.setDate(index, Date.valueOf(value));
    } else if (isTime(value)) {
      stmt.setTime(index, Time.valueOf(value));
    } else if (isBool(value)) {
      stmt.setBoolean(index, value.equals("true"));
    } else {
      stmt.setString(index, value);
    }
  }

  static void addToStatement(int index, PreparedStatement stmt, Object value) throws SQLException {
    if (value instanceof Integer) {
      stmt.setInt(index, (java.lang.Integer)value);
    } else if (value instanceof LocalDate) {
      var str = ((LocalDate)value).toString("yyyy-MM-dd");
      stmt.setDate(index, Date.valueOf(str));
    } else if (value instanceof LocalTime) {
      var str = ((LocalTime)value).toString("HH:mm:ss");
      stmt.setTime(index, Time.valueOf(str));
    } else if (value instanceof Boolean) {
      stmt.setBoolean(index, (Boolean)value);
    } else {
      stmt.setString(index, value.toString());
    }
  }

}
