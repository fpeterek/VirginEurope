package org.fpeterek.virgineurope.orm;

public class Attribute {
  public String name;
  public String table;

  public String fullName() {
    return String.format("%s.%s", table, name);
  }

  public Attribute(String attrName, String tableName) {
    name = attrName;
    table = tableName;
  }

  public BooleanExpr eq(Attribute attr) {
    return new BooleanExpr(fullName(), "=", attr.fullName());
  }

  public BooleanExpr like(String other) {
    return new BooleanExpr(fullName(), "LIKE", other, true);
  }

  public BooleanExpr ilike(String other) {
    return new BooleanExpr(fullName(), "ILIKE", other, true);
  }

  /* Integer overloads */

  public BooleanExpr eq(Object other) {
    return new BooleanExpr(fullName(), "=", other, true);
  }

  public BooleanExpr neq(Object other) {
    return new BooleanExpr(fullName(), "!=", other, true);
  }

  public BooleanExpr lt(Object other) {
    return new BooleanExpr(fullName(), "<", other, true);
  }

  public BooleanExpr lte(Object other) {
    return new BooleanExpr(fullName(), "<=", other, true);
  }

  public BooleanExpr gt(Object other) {
    return new BooleanExpr(fullName(), ">", other, true);
  }

  public BooleanExpr gte(Object other) {
    return new BooleanExpr(fullName(), ">=", other, true);
  }
}
