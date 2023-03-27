package com.example.testbusticket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  List<Reservation> findByClient(Client client);

  List<Reservation> findByTravelDate(LocalDate travelDate);

  List<Reservation> findByClientIdAndTravelDate(Long clientId, LocalDate travelDate);



}
