package com.example.testbusticket.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "travel_date", nullable = false)
  private LocalDate travelDate;

  @ManyToMany()
  private Set<Bus> buses;

  @Column(name = "total_cost", nullable = false)
  private BigDecimal totalCost;

  @Column(name = "bill_generated")
  private Boolean billGenerated;


  public Reservation() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public LocalDate getTravelDate() {
    return travelDate;
  }

  public void setTravelDate(LocalDate travelDate) {
    this.travelDate = travelDate;
  }

  public Set<Bus> getBuses() {
    return buses;
  }

  public void setBuses(Set<Bus> buses) {
    this.buses = buses;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public Boolean getBillGenerated() {
    return billGenerated;
  }

  public void setBillGenerated(Boolean billGenerated) {
    this.billGenerated = billGenerated;
  }

  public BigDecimal computeTotalCost() {
    BigDecimal totalCost = BigDecimal.ZERO;
    Set<Bus> trips = this.getBuses();
    BigDecimal hundred = new BigDecimal("100");
    for (Bus trip : trips) {
      if(trip.getPrice().compareTo(hundred) > 0){
        totalCost = totalCost.add(trip.getPrice().multiply(new BigDecimal(0.95)));
      }else{
        totalCost = totalCost.add(trip.getPrice());
      }

    }
    return totalCost;
  }
}