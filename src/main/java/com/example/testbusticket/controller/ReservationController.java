package com.example.testbusticket.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.testbusticket.dto.ReservationDTO;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.repository.BusRepository;
import com.example.testbusticket.repository.ClientRepository;
import com.example.testbusticket.service.ReservationService;
import com.example.testbusticket.util.PaymentMethod;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;
  private final BusRepository busRepository;
  private final ClientRepository clientRepository;

  @Autowired
  public ReservationController(ReservationService reservationService, BusRepository busRepository,
      ClientRepository clientRepository) {
    this.reservationService = reservationService;
    this.busRepository = busRepository;
    this.clientRepository = clientRepository;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
    Reservation reservation = reservationService.getReservationById(id);
    if (reservation != null) {
      return ResponseEntity.ok(reservation);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<Reservation>> getAllReservations() {
    List<Reservation> reservations = reservationService.getAllReservations(0, 0);
    if (!reservations.isEmpty()) {
      return ResponseEntity.ok(reservations);
    }
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
    Client client = clientRepository.findClientById(Long.valueOf(reservationDTO.getClientId()));

    Set<Bus> trips = reservationDTO.getBusesIds().stream().map(busRepository::findById).filter(Optional::isPresent)
        .map(Optional::get).collect(Collectors.toSet());

    Reservation reservation = reservationService.createReservation(client, reservationDTO.getTravelDate(), trips);
    return ResponseEntity.ok(reservation);
  }

  @PostMapping("/{id}/pay/{wayPayement}")
  public ResponseEntity<String> payReservation(@PathVariable Long id, @PathVariable String wayPayement) {
    PaymentMethod paymentMethod = PaymentMethod.valueOf(wayPayement);
    boolean isPaid = reservationService.payReservation(id, paymentMethod);
    if (isPaid) {
      return ResponseEntity.ok("Reservation paid successfully");
    }
    return ResponseEntity.badRequest().body("Reservation payment failed");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    reservationService.deleteReservation(id);
    return ResponseEntity.noContent().build();
  }
}