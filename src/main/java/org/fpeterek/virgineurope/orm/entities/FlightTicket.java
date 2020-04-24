package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.TravelClass;
import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

import java.util.Objects;

public class FlightTicket extends Entity {

  String meal;
  String seat;
  TravelClass travelClass;
  int baggageAllowance;

  int operatedId;
  OperatedFlight operatedFlight;
  int passengerId;
  Passenger passenger;

  public FlightTicket(String meal, String seat, TravelClass cls, int allowance,
                      int opId, OperatedFlight opFlight, int paxId, Passenger pax) {

    this.meal = meal;
    this.seat = seat;
    travelClass = cls;
    baggageAllowance = allowance;
    operatedId = opId;
    operatedFlight = opFlight;
    passengerId = paxId;
    passenger = pax;

  }

  public String getMeal() { return meal; }
  public String getSeat() { return seat; }
  public TravelClass getTravelClass() { return travelClass; }
  public int getBaggageAllowance() { return baggageAllowance; }
  public int getOperatedId() { return operatedId; }
  public OperatedFlight getOperatedFlight() { return operatedFlight; }
  public int getPassengerId() { return passengerId; }
  public Passenger getPassenger() { return passenger; }

  private BooleanExpr dbMatchCond() {
    var flCond = VU.passengerOnFlight.operatedId.eq(String.valueOf(operatedId));
    var paxCond = VU.passengerOnFlight.passengerId.eq(String.valueOf(passengerId));
    return flCond.or(paxCond);
  }

  @Override
  public void formDelete(Delete query) {
    query.where(dbMatchCond());
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.passengerOnFlight.passengerId, String.valueOf(passengerId))
        .set(VU.passengerOnFlight.operatedId, String.valueOf(operatedId))
        .set(VU.passengerOnFlight.baggageAllowance, String.valueOf(baggageAllowance))
        .set(VU.passengerOnFlight.meal, meal)
        .set(VU.passengerOnFlight.seat, seat)
        .set(VU.passengerOnFlight.travelClass, travelClass.dbValue())
        .where(dbMatchCond());
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.passengerOnFlight.passengerId, VU.passengerOnFlight.operatedId,
        VU.passengerOnFlight.baggageAllowance, VU.passengerOnFlight.meal, VU.passengerOnFlight.seat,
        VU.passengerOnFlight.travelClass)
        .values(String.valueOf(passengerId), String.valueOf(operatedId), String.valueOf(baggageAllowance),
            meal, seat, travelClass.dbValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatedId, passengerId);
  }

  @Override
  public void add(Entity entity) {
    if (entity instanceof OperatedFlight) {
      operatedFlight = (OperatedFlight)entity;
    }
    else if (entity instanceof Passenger) {
      passenger = (Passenger)entity;
    }
  }

  @Override
  public String toString() {
    return "FlightTicket{" +
            "meal='" + meal + '\'' +
            ", seat='" + seat + '\'' +
            ", travelClass=" + travelClass +
            ", baggageAllowance=" + baggageAllowance +
            ", operatedId=" + operatedId +
            ", operatedFlight=" + operatedFlight +
            ", passengerId=" + passengerId +
            ", passenger='" + (passenger == null ? "null" : passenger.fullName()) + '\'' +
            '}';
  }
}
