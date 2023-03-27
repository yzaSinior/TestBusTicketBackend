package com.example.testbusticket.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.testbusticket.util.PaymentMethod;

@Entity
@Table(name = "bill")
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "reservation_id", nullable = false)
  private Reservation reservation;

  @Column(name = "payment_method", nullable = false)
  private PaymentMethod paymentMethod;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "payment_date", nullable = false)
  private LocalDate paymentDate;

  public Bill() {
  }

  public Bill(Long id, Reservation reservation, Client client, PaymentMethod paymentMethod) {
    this.id = id;
    this.reservation = reservation;
    this.client = client;
    this.paymentMethod = paymentMethod;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }
}