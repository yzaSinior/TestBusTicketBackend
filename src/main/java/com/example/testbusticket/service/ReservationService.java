package com.example.testbusticket.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.example.testbusticket.model.Bus;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.util.PaymentMethod;

public interface ReservationService {

  Reservation createReservation(Client client, LocalDate travelDate, Set<Bus> trips);

  Reservation getReservationById(Long id);

  List<Reservation> getAllReservations(int page, int size);

  Reservation updateReservation(Reservation reservation);

  void deleteReservation(Long id);

  BigDecimal calculateTotalCost(Reservation reservation);

  boolean payReservation(Long reservationId, PaymentMethod paymentMethod);
}