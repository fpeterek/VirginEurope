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

  /*public BooleanExpr lt(Attribute attr) {
    return new BooleanExpr(fullName(), "<", attr.fullName());
  }

  public BooleanExpr lte(Attribute attr) {
    return new BooleanExpr(fullName(), "<=", attr.fullName());
  }

  public BooleanExpr gt(Attribute attr) {
    return new BooleanExpr(fullName(), ">", attr.fullName());
  }

  public BooleanExpr gte(Attribute attr) {
    return new BooleanExpr(fullName(), ">=", attr.fullName());
  }*/

  public BooleanExpr eq(String other) {
    return new BooleanExpr(fullName(), "=", other, true);
  }

  public BooleanExpr neq(String other) {
    return new BooleanExpr(fullName(), "!=", other, true);
  }

  public BooleanExpr lt(String other) {
    return new BooleanExpr(fullName(), "<", other, true);
  }

  public BooleanExpr lte(String other) {
    return new BooleanExpr(fullName(), "<=", other, true);
  }

  public BooleanExpr gt(String other) {
    return new BooleanExpr(fullName(), ">", other, true);
  }

  public BooleanExpr gte(String other) {
    return new BooleanExpr(fullName(), ">=", other, true);
  }

  public BooleanExpr like(String other) {
    return new BooleanExpr(fullName(), "LIKE", other, true);
  }

  /* Integer overloads */

  public BooleanExpr eq(int other) {
    return new BooleanExpr(fullName(), "=", String.valueOf(other), true);
  }

  public BooleanExpr neq(int other) {
    return new BooleanExpr(fullName(), "!=", String.valueOf(other), true);
  }

  public BooleanExpr lt(int other) {
    return new BooleanExpr(fullName(), "<", String.valueOf(other), true);
  }

  public BooleanExpr lte(int other) {
    return new BooleanExpr(fullName(), "<=", String.valueOf(other), true);
  }

  public BooleanExpr gt(int other) {
    return new BooleanExpr(fullName(), ">", String.valueOf(other), true);
  }

  public BooleanExpr gte(int other) {
    return new BooleanExpr(fullName(), ">=", String.valueOf(other), true);
  }
}
