package com.example.testbusticket.dto;

import java.time.LocalDate;
import java.util.List;

public class ReservationDTO {

  private String clientId;
  private LocalDate travelDate;
  private List<Long> busesIds;

  public LocalDate getTravelDate() {
    return travelDate;
  }

  public void setTravelDate(LocalDate travelDate) {
    this.travelDate = travelDate;
  }

  public List<Long> getBusesIds() {
    return busesIds;
  }

  public void setBusesIds(List<Long> busesIds) {
    this.busesIds = busesIds;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
}
