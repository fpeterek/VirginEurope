package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.TravelClass;
import org.fpeterek.virgineurope.orm.BooleanExpr;
import org.fpeterek.virgineurope.orm.VU;
import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

import java.util.Objects;

public class FlightTicket extends Entity {

  int ticketId;
  String meal;
  String seat;
  TravelClass travelClass;
  int baggageAllowance;

  int operatedId;
  OperatedFlight operatedFlight;
  int passengerId;
  Passenger passenger;

  public FlightTicket(int id, String meal, String seat, TravelClass cls, int allowance,
                      int opId, OperatedFlight opFlight, int paxId, Passenger pax) {

    ticketId = id;
    this.meal = meal;
    this.seat = seat;
    travelClass = cls;
    baggageAllowance = allowance;
    operatedId = opId;
    operatedFlight = opFlight;
    passengerId = paxId;
    passenger = pax;

  }

  public int getTicketId() { return ticketId; }
  public String getMeal() { return meal; }
  public String getSeat() { return seat; }
  public TravelClass getTravelClass() { return travelClass; }
  public int getBaggageAllowance() { return baggageAllowance; }
  public int getOperatedId() { return operatedId; }
  public OperatedFlight getOperatedFlight() { return operatedFlight; }
  public int getPassengerId() { return passengerId; }
  public Passenger getPassenger() { return passenger; }

  public void setMeal(String newMeal) { meal = newMeal; }
  public void setSeat(String newSeat) { seat = newSeat; }
  public void setBaggageAllowance(int allowance) { baggageAllowance = allowance; }
  public void setOperatedId(int newFlight) { operatedId = newFlight; }
  public void setTravelClass(TravelClass cls) { travelClass = cls; }
  public void setPassengerId(int newPax) { passengerId = newPax; }

  private BooleanExpr dbMatchCond() {
    return VU.flightTicket.ticketId.eq(ticketId);
  }

  @Override
  public void formDelete(Delete query) {
    query.where(dbMatchCond());
  }

  @Override
  public void formUpdate(Update query) {
    query
        .set(VU.flightTicket.passengerId, passengerId)
        .set(VU.flightTicket.operatedId, operatedId)
        .set(VU.flightTicket.baggageAllowance, baggageAllowance)
        .set(VU.flightTicket.meal, meal)
        .set(VU.flightTicket.seat, seat)
        .set(VU.flightTicket.travelClass, travelClass.dbValue())
        .where(dbMatchCond());
  }

  @Override
  public void formInsert(Insert query) {
    query.attributes(VU.flightTicket.passengerId, VU.flightTicket.operatedId,
        VU.flightTicket.baggageAllowance, VU.flightTicket.meal, VU.flightTicket.seat,
        VU.flightTicket.travelClass)
        .values(passengerId, operatedId, baggageAllowance,
            meal, seat, travelClass.dbValue());
  }

  @Override
  public int hashCode() {
    return ticketId;
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
        "ticketId=" + ticketId +
        ", meal='" + meal + '\'' +
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
