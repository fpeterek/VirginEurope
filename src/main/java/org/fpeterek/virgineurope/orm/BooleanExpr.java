package org.fpeterek.virgineurope.orm;

public class BooleanExpr {

  public final String left;
  public final String right;
  public final String operator;

  BooleanExpr(String l, String r, String op) {
    left = l;
    right = r;
    operator = op;
  }

  public BooleanExpr and(BooleanExpr other) {
    return new BooleanExpr(toString(), "AND", other.toString());
  }

  public BooleanExpr or(BooleanExpr other) {
    return new BooleanExpr(toString(), "OR", other.toString());
  }

  @Override
  public String toString() {
    return String.format("%s %s %s", left, right, operator);
  }
}
