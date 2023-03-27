package com.example.testbusticket.controller.test;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testbusticket.controller.BusController;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.service.BusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(BusController.class)
public class BusControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BusService busService;

  @Test
  public void testGetAllBuses() throws Exception {
    // Given
    List<Bus> buses = Arrays.asList(
        new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston"),
        new Bus("BUS456", LocalDate.of(2023, 3, 26), 40, LocalTime.of(10, 0), new BigDecimal(25), "Boston", "New York")
    );

    // When
    when(busService.getAllBuses()).thenReturn(buses);

    // Then
    mockMvc.perform(get("/api/buses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].busNumber", is("BUS123")))
        .andExpect(jsonPath("$[1].busNumber", is("BUS456")));
  }

  @Test
  public void testGetBusById() throws Exception {
    // Given
    Long id = 1L;
    Bus bus = new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston");

    // When
    when(busService.getBusById(id)).thenReturn(bus);

    // Then
    mockMvc.perform(get("/api/buses/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.busNumber", is("BUS123")))
        .andExpect(jsonPath("$.numberOfSeats", is(50)))
        .andExpect(jsonPath("$.price", is(20)))
        .andExpect(jsonPath("$.departurePoint", is("New York")))
        .andExpect(jsonPath("$.arrivalPoint", is("Boston")));
  }

  @Test
  public void testAddBus() throws Exception {
    // Given
    Bus bus = new Bus("BUS123", LocalDate.of(2023, 3, 25), 50, LocalTime.of(9, 0), new BigDecimal(20), "New York", "Boston");

    // When
    when(busService.addBus(any())).thenReturn(bus);

    // Then
    mockMvc.perform(post("/api/buses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(bus)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("/api/buses/")));
  }

  @Test
  public void testDeleteBus() throws Exception {

    // Given
    Long id = 1L;

    // When
    mockMvc.perform(delete("/api/buses/{id}", id))
        .andExpect(status().isNoContent());

    // Then
    verify(busService, times(1)).deleteBus(id);
  }

  @Test
  public void testUpdateBus() throws Exception {
    // Given
    Long id = 1L;
    Bus updatedBus = new Bus("BUS456", LocalDate.of(2023, 3, 26), 40, LocalTime.of(10, 0), new BigDecimal(25), "Boston", "New York");

    // When
    when(busService.updateBus(eq(id), any())).thenReturn(updatedBus);

    // Then
    mockMvc.perform(put("/api/buses/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updatedBus)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.busNumber", is("BUS456")))
        .andExpect(jsonPath("$.numberOfSeats", is(40)))
        .andExpect(jsonPath("$.price", is(25)))
        .andExpect(jsonPath("$.departurePoint", is("Boston")))
        .andExpect(jsonPath("$.arrivalPoint", is("New York")));
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
