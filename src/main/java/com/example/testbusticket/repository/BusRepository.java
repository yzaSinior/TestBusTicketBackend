package com.example.testbusticket.repository;

import java.util.List;

import com.example.testbusticket.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Bus} entity.
 */
@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

  List<Bus> findByDepartureTime(String departureTime);

  List<Bus> findByPriceGreaterThan(double price);

  List<Bus> findByNumberOfSeatsGreaterThanEqual(int numberOfSeats);

  Bus findByBusNumber(String number);

  boolean existsByBusNumber(String number);
}
