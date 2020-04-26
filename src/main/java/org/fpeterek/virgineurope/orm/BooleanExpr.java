package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.sql.Util;

import java.util.ArrayList;
import java.util.List;

public class BooleanExpr {

  public final String left;
  public final String right;
  public final String operator;
  public final List<Object> parameters = new ArrayList<>();

  BooleanExpr(String l, String op, String r) {
    left = l;
    right = r;
    operator = op;
  }

  BooleanExpr(String l, String op, Object r, boolean addToParams) {
    left = l;
    right = "?";
    operator = op;
    if (addToParams) {
      parameters.add(r);
    }
  }

  private void mergeParams(BooleanExpr other) {
    parameters.addAll(other.parameters);
  }

  private BooleanExpr mergeWith(String op, BooleanExpr other) {
    var res = new BooleanExpr(toParametrizedString(), op, other.toParametrizedString());
    res.mergeParams(this);
    res.mergeParams(other);
    return res;
  }

  public BooleanExpr and(BooleanExpr other) {
    return mergeWith("AND", other);
  }

  public BooleanExpr or(BooleanExpr other) {
    return mergeWith("OR", other);
  }

  @Override
  public String toString() {
    return toFormattedString();
  }

  public String toParametrizedString() {
    return String.format("(%s %s %s)", left, operator, right);
  }

  public String toFormattedString() {

    var parametrized = toParametrizedString();

    for (Object param : parameters) {
      parametrized = parametrized.replaceFirst("\\?", Util.format(param));
    }

    return parametrized;
  }

}
