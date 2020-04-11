package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.common.TravelClass;

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
            ", passenger='" + passenger.fullName() + '\'' +
            '}';
  }
}
