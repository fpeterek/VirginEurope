package org.fpeterek.virgineurope.orm.sql.custom;

public class AssignPilotsQuery extends BooleanResQuery {
  public AssignPilotsQuery(int id) {
    super("AssignPilotsQuery", "AssignPilots", id);
  }
}
