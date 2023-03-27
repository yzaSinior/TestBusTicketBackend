package com.example.testbusticket.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.testbusticket.exception.BusNotFoundException;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.repository.BusRepository;
import com.example.testbusticket.service.impl.BusServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {

  @Mock
  private BusRepository busRepository;

  @InjectMocks
  private BusServiceImpl busService;

  @Test
  public void testGetAllBuses() {

    // Given
    List<Bus> buses = Arrays.asList(
        new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston"),
        new Bus("BUS456", LocalDate.of(2023, 3, 26), 40, LocalTime.of(10, 0), new BigDecimal(25), "Boston", "New York")
    );

    // When
    when(busRepository.findAll()).thenReturn(buses);
    List<Bus> result = busService.getAllBuses();

    // Then
    assertEquals(buses.size(), result.size());
    assertEquals(buses.get(0).getBusNumber(), result.get(0).getBusNumber());
    assertEquals(buses.get(1).getBusNumber(), result.get(1).getBusNumber());

  }

  @Test
  public void testGetBusById() throws BusNotFoundException {

    // Given
    Long id = 1L;
    Bus bus = new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston");

    // When
    when(busRepository.findById(id)).thenReturn(Optional.of(bus));
    Bus result = busService.getBusById(id);

    // Then
    assertEquals(bus.getBusNumber(), result.getBusNumber());
    assertEquals(bus.getNumberOfSeats(), result.getNumberOfSeats());
    assertEquals(bus.getPrice(), result.getPrice());
    assertEquals(bus.getDeparturePoint(), result.getDeparturePoint());
    assertEquals(bus.getArrivalPoint(), result.getArrivalPoint());

  }

  @Test
  public void testGetBusByIdNotFound() {
    // Given
    Long id = 1L;

    // When
    when(busRepository.findById(id)).thenReturn(Optional.empty());

    // Then
    assertThrows(BusNotFoundException.class, () -> {
      busService.getBusById(id);
    });

  }

  @Test
  public void testAddBus() {

    // Given
    Bus bus = new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston");

    // When
    when(busRepository.save(any())).thenReturn(bus);
    Bus result = busService.addBus(bus);

    // Then
    assertEquals(bus.getBusNumber(), result.getBusNumber());
    assertEquals(bus.getNumberOfSeats(), result.getNumberOfSeats());
    assertEquals(bus.getPrice(), result.getPrice());
    assertEquals(bus.getDeparturePoint(), result.getDeparturePoint());
    assertEquals(bus.getArrivalPoint(), result.getArrivalPoint());

  }

  @Test
  public void testUpdateBus() throws BusNotFoundException {

    // Given
    Long id = 1L;
    Bus bus = new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston");
    Bus busDetails = new Bus("BUS123", LocalDate.of(2023, 3, 25), 40, LocalTime.of(9, 0), new BigDecimal(25), "New York", "Boston");

    // When
    when(busRepository.findById(id)).thenReturn(Optional.of(bus));
    when(busRepository.save(bus)).thenReturn(bus);
    Bus result = busService.updateBus(id, busDetails);

    // Then
    assertEquals(busDetails.getNumberOfSeats(), result.getNumberOfSeats());
    assertEquals(busDetails.getPrice(), result.getPrice());

  }

}