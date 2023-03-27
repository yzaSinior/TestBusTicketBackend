package com.example.testbusticket.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.testbusticket.exception.ReservationException;
import com.example.testbusticket.model.*;
import com.example.testbusticket.repository.ReservationRepository;
import com.example.testbusticket.service.*;
import com.example.testbusticket.util.PaymentMethod;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final BillService billService;
  private final PaymentStrategySelector paymentStrategySelector;


  public ReservationServiceImpl(ReservationRepository reservationRepository,
      BillService billService, PaymentStrategySelector paymentStrategySelector) {

    this.reservationRepository = reservationRepository;
    this.billService = billService;
    this.paymentStrategySelector = paymentStrategySelector;
  }

  @Override
  public Reservation createReservation(Client client, LocalDate travelDate, Set<Bus> buses) {
    Reservation reservation = new Reservation();
    reservation.setClient(client);
    reservation.setTravelDate(travelDate);
    reservation.setBuses(buses);
    reservation.setBillGenerated(false);
    reservation.setTotalCost(calculateTotalCost(reservation));
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation getReservationById(Long id) {
    return reservationRepository.findById(id)
        .orElseThrow(() -> new ReservationException("Reservation not found with id " + id));
  }

  @Override
  public List<Reservation> getAllReservations(int page, int size) {
    // TODO : implement pagination
    return reservationRepository.findAll();
  }

  @Override
  public Reservation updateReservation(Reservation reservation) {
		Reservation existingReservation = getReservationById(reservation.getId());

    existingReservation.setClient(reservation.getClient());
    existingReservation.setTravelDate(reservation.getTravelDate());
    existingReservation.setBuses(reservation.getBuses());
    existingReservation.setTotalCost(calculateTotalCost(existingReservation));
    return reservationRepository.save(existingReservation);

  }

  @Override
  public void deleteReservation(Long id) {
    Optional<Reservation> reservationOptional = reservationRepository.findById(id);
    if (reservationOptional.isPresent()) {
      reservationRepository.deleteById(id);
    } else {
      throw new ReservationException("Reservation with id " + id + " not found");
    }
  }

  @Override
  public BigDecimal calculateTotalCost(Reservation reservation) {
    return reservation.computeTotalCost();
  }



  @Override
  public boolean payReservation(Long reservationId, PaymentMethod paymentMethod) {

    Reservation reservation = this.getReservationById(reservationId);
    if (!reservation.getBillGenerated() && payReservation(paymentMethod, reservation.getTotalCost())) {


      Bill bill = new Bill();
      bill.setReservation(reservation);
      bill.setClient(reservation.getClient());
      bill.setPaymentMethod(paymentMethod);
      bill.setPaymentDate(LocalDate.now());

      billService.addBill(bill);

      reservation.setBillGenerated(true);
      reservationRepository.save(reservation);

      return true;

    }
    return false;
  }

  private Boolean payReservation(PaymentMethod paymentMethod, BigDecimal amount) {
    PaymentService paymentStrategy = paymentStrategySelector.select(paymentMethod);
    if (paymentStrategy == null) {
      throw new IllegalArgumentException("Payment method not recognized");
    }
    return paymentStrategy.pay(amount);
  }
}