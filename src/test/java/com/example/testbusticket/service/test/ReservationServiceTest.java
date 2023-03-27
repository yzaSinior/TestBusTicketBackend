package com.example.testbusticket.service.test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.repository.ReservationRepository;
import com.example.testbusticket.service.impl.ReservationServiceImpl;
import com.example.testbusticket.util.PaymentMethod;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;

  @InjectMocks
  private ReservationServiceImpl reservationService;

  @Test
  public void createReservationTest() {
    // Given
    Client client = new Client("John Doe", "johndoe@example.com");
    LocalDate travelDate = LocalDate.of(2023, Month.APRIL, 15);
    Set<Bus> buses = new HashSet<>();
    buses.add(new Bus("BUS1", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston"));
    buses.add(new Bus("BUS2", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston"));

    Reservation expectedReservation = new Reservation();
    expectedReservation.setId(1L);
    expectedReservation.setClient(client);
    expectedReservation.setTravelDate(travelDate);
    expectedReservation.setBuses(buses);
    expectedReservation.setTotalCost(new BigDecimal(1100));

    given(reservationRepository.save(any(Reservation.class))).willReturn(expectedReservation);

    // When
    Reservation actualReservation = reservationService.createReservation(client, travelDate, buses);

    // Then
    assertThat(actualReservation).isEqualTo(expectedReservation);
    verify(reservationRepository, times(1)).save(any(Reservation.class));
  }

  @Test
  public void getReservationById() {
    // Given
    Reservation expectedReservation = new Reservation();
    expectedReservation.setId(1L);
    given(reservationRepository.findById(1L)).willReturn(Optional.of(expectedReservation));

    // When
    Reservation actualReservation = reservationService.getReservationById(1L);

    // Then
    assertThat(actualReservation).isEqualTo(expectedReservation);
    verify(reservationRepository, times(1)).findById(1L);
  }

  @Test
  public void getAllReservationsTest() {
    // Given
    Reservation reservation1 = new Reservation();
    reservation1.setId(1L);
    Reservation reservation2 = new Reservation();
    reservation2.setId(2L);
    List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

    given(reservationRepository.findAll()).willReturn(expectedReservations);

    // When
    List<Reservation> actualReservations = reservationService.getAllReservations(0, 10);

    // Then
    assertThat(actualReservations).isEqualTo(expectedReservations);
    verify(reservationRepository, times(1)).findAll();
  }

  @Test
  public void updateReservationTest() {
    // Given
    Reservation existingReservation = new Reservation();
    existingReservation.setId(1L);
    existingReservation.setClient(new Client("John Doe", "johndoe@example.com"));
    existingReservation.setTravelDate(LocalDate.now());
    Set<Bus> buses = new HashSet<>();
    buses.add(new Bus("BUS 1", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York",
        "Boston"));
    existingReservation.setBuses(buses);
    existingReservation.setTotalCost(BigDecimal.valueOf(1000));

    Reservation updatedReservation = new Reservation();
    updatedReservation.setId(1L);
    updatedReservation.setClient(new Client("Jane Doe", "janedoe@example.com"));
    updatedReservation.setTravelDate(LocalDate.now().plusDays(1));
    buses = new HashSet<>();
    buses.add(new Bus("BUS 2", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York",
        "Boston"));
    updatedReservation.setBuses(buses);
    updatedReservation.setTotalCost(BigDecimal.valueOf(2000));

    given(reservationRepository.findById(1L)).willReturn(Optional.of(existingReservation));
    given(reservationRepository.save(existingReservation)).willReturn(updatedReservation);

    // When
    Reservation result = reservationService.updateReservation(updatedReservation);

    // Then
    verify(reservationRepository, times(1)).findById(1L);
    verify(reservationRepository, times(1)).save(existingReservation);
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getClient().getName()).isEqualTo("Jane Doe");
    assertThat(result.getClient().getEmail()).isEqualTo("janedoe@example.com");
    assertThat(result.getTravelDate()).isEqualTo(LocalDate.now().plusDays(1));
    assertThat(result.getBuses().size()).isEqualTo(1);
    assertThat(result.getTotalCost()).isEqualTo(BigDecimal.valueOf(2000));
  }

  @Test
  public void payReservationTest() {
    // Given
    Reservation reservation = new Reservation();
    reservation.setId(1L);
    reservation.setClient(new Client("John Doe", "johndoe@example.com"));
    reservation.setTravelDate(LocalDate.now());
    Set<Bus> buses = new HashSet<>();
    buses.add(new Bus("BUS 2", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York",
        "Boston"));
    reservation.setBuses(buses);
    reservation.setTotalCost(BigDecimal.valueOf(1000));
    given(reservationRepository.findById(1L)).willReturn(Optional.of(reservation));

    // When
    boolean result = reservationService.payReservation(1L, PaymentMethod.CREDIT_CARD);

    // Then
    verify(reservationRepository, times(1)).findById(1L);
    assertThat(result).isEqualTo(false);
  }

}
