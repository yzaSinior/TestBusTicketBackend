package com.example.testbusticket.controller.test;


import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.example.testbusticket.controller.BillController;
import com.example.testbusticket.model.Bill;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.service.BillService;
import com.example.testbusticket.util.PaymentMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(BillController.class)
class BillControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BillService billService;

  @Test
  public void testGetAllBills() throws Exception {
    // given
    Bill bill1 = new Bill(1L, new Reservation(), new Client(), PaymentMethod.CREDIT_CARD);
    Bill bill2 = new Bill(2L, new Reservation(), new Client(), PaymentMethod.PAYPAL);
    List<Bill> bills = Arrays.asList(bill1, bill2);
    BDDMockito.given(billService.getAllBills()).willReturn(bills);

    // when
    mockMvc.perform(get("/api/bills"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].paymentMethod", is("CREDIT_CARD")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].paymentMethod", is("PAYPAL")));

    // then
    verify(billService, times(1)).getAllBills();
  }

  @Test
  public void testGetBillById() throws Exception {
    // given
    Long billId = 1L;
    Bill bill = new Bill(billId, new Reservation(), new Client(), PaymentMethod.CREDIT_CARD);
    given(billService.getBillById(billId)).willReturn(bill);

    // when
    mockMvc.perform(get("/api/bills/{id}", billId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.paymentMethod", is("CREDIT_CARD")));

    // then
    verify(billService, times(1)).getBillById(eq(billId));
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
