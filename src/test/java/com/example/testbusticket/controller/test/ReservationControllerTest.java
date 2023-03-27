package com.example.testbusticket.controller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testbusticket.controller.ReservationController;

import com.example.testbusticket.dto.ReservationDTO;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.repository.BusRepository;
import com.example.testbusticket.repository.ClientRepository;
import com.example.testbusticket.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReservationService reservationService;

  @MockBean
  private BusRepository busRepository;

  @MockBean
  private ClientRepository clientRepository;

  @Test
  public void testGetReservationById() throws Exception {
    // Given
    Long reservationId = 1L;
    Reservation reservation = new Reservation();
    reservation.setId(reservationId);

    given(reservationService.getReservationById(reservationId)).willReturn(reservation);

    // When
    mockMvc.perform(get("/api/reservations/{id}", reservationId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(reservationId.intValue())));

    // Then
    then(reservationService).should().getReservationById(reservationId);
    verifyNoMoreInteractions(reservationService);
  }

  @Test
  public void testGetAllReservations() throws Exception {
    // Given
    Reservation reservation1 = new Reservation();
    Reservation reservation2 = new Reservation();
    List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

    given(reservationService.getAllReservations(0, 0)).willReturn(reservations);

    // When
    mockMvc.perform(get("/api/reservations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));

    // Then
    then(reservationService).should().getAllReservations(0, 0);
    verifyNoMoreInteractions(reservationService);
  }

  @Test
  public void testCreateReservation() throws Exception {
    // Given
    Client client = new Client();
    client.setId(1L);
    given(clientRepository.findClientById(1L)).willReturn(client);

    Bus bus1 = new Bus();
    bus1.setId(1L);
    Bus bus2 = new Bus();
    bus2.setId(2L);
    Set<Bus> buses = new HashSet<>(Arrays.asList(bus1, bus2));
    given(busRepository.findById(1L)).willReturn(Optional.of(bus1));
    given(busRepository.findById(2L)).willReturn(Optional.of(bus2));

    ReservationDTO reservationDTO = new ReservationDTO();
    reservationDTO.setClientId("1");
    reservationDTO.setTravelDate(LocalDate.now());
    reservationDTO.setBusesIds(Arrays.asList(1L, 2L));

    Reservation reservation = new Reservation();
    reservation.setId(1L);
    given(reservationService.createReservation(client, reservationDTO.getTravelDate(), buses)).willReturn(reservation);

    // When
    mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(reservationDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(reservation.getId().intValue())));

    // Then
    then(clientRepository).should().findClientById(1L);
    then(busRepository).should(times(2)).findById(anyLong());
    then(reservationService).should().createReservation(client, reservationDTO.getTravelDate(), buses);
  }

  private static String asJsonString(Object object) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}