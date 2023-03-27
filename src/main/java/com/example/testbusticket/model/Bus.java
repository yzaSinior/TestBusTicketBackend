package com.example.testbusticket.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bus")
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "bus_number", unique = true, nullable = false)
  private String busNumber;

  @Column(name = "travel_date", nullable = false)
  private LocalDate travelDate;

  @Column(name = "number_seats", nullable = false)
  private Integer numberOfSeats;

  @Column(name = "departure_time", nullable = false)
  private LocalTime departureTime;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "departure_point", nullable = false)
  private String departurePoint;

  @Column(name = "arrival_point", nullable = false)
  private String arrivalPoint;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "reservation_id")
  private Reservation reservation;


  public Bus() {
  }

  public Bus(String busNumber, LocalDate travelDate, Integer numberOfSeats, LocalTime departureTime, BigDecimal price, String departurePoint, String arrivalPoint) {
    this.busNumber = busNumber;
    this.travelDate = travelDate;
    this.numberOfSeats = numberOfSeats;
    this.departureTime = departureTime;
    this.price = price;
    this.departurePoint = departurePoint;
    this.arrivalPoint = arrivalPoint;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBusNumber() {
    return busNumber;
  }

  public void setBusNumber(String busNumber) {
    this.busNumber = busNumber;
  }

  public LocalDate getTravelDate() {
    return travelDate;
  }

  public void setTravelDate(LocalDate journeyDate) {
    this.travelDate = journeyDate;
  }

  public Integer getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getDeparturePoint() {
    return departurePoint;
  }

  public void setDeparturePoint(String departurePoint) {
    this.departurePoint = departurePoint;
  }

  public String getArrivalPoint() {
    return arrivalPoint;
  }

  public void setArrivalPoint(String arrivalPoint) {
    this.arrivalPoint = arrivalPoint;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  @Override
  public String toString() {
    return "Bus{" + "id=" + id + ", busNumber='" + busNumber + '\'' + ", travelDate=" + travelDate + ", numberOfSeats="
        + numberOfSeats + ", departureTime=" + departureTime + ", price=" + price + ", departurePoint='"
        + departurePoint + '\'' + ", arrivalPoint='" + arrivalPoint + '\'' + '}';
  }
}