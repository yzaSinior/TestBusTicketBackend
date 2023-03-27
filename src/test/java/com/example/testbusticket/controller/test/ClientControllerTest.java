package com.example.testbusticket.controller.test;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testbusticket.controller.ClientController;
import com.example.testbusticket.exception.ClientNotFoundException;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.service.ClientService;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @Test
  public void testGetClientById() throws Exception {
    // Given
    Long id = 1L;
    Client client = new Client(id, "John Doe", "johndoe@example.com");
    given(clientService.getClientById(id)).willReturn(client);

    // When
    mockMvc.perform(get("/api/clients/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id.intValue())))
        .andExpect(jsonPath("$.name", is(client.getName())))
        .andExpect(jsonPath("$.email", is(client.getEmail())));

    // Then
    verify(clientService, times(1)).getClientById(id);
  }

  @Test
  public void testGetClientByIdNotFound() throws Exception {
    // Given
    Long id = 1L;
    given(clientService.getClientById(id)).willThrow(new ClientNotFoundException(id));

    // When
    mockMvc.perform(get("/api/clients/{id}", id))
        .andExpect(status().isNotFound());

    // Then
    verify(clientService, times(1)).getClientById(id);
  }

  @Test
  public void testUpdateClient() throws Exception {
    // Given
    Long id = 1L;
    Client client = new Client(id, "John Doe", "johndoe@example.com");
    given(clientService.updateClient(eq(id), any(Client.class))).willReturn(client);
    String jsonRequest = "{ \"name\": \"" + client.getName() + "\", \"email\": \"" + client.getEmail() + "\" }";

    // When
    mockMvc.perform(put("/api/clients/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id.intValue())))
        .andExpect(jsonPath("$.name", is(client.getName())))
        .andExpect(jsonPath("$.email", is(client.getEmail())));

    // Then
    verify(clientService, times(1)).updateClient(eq(id), any(Client.class));
  }

  @Test
  public void testDeleteClient() throws Exception {
    // Given
    long clientId = 1L;
    doNothing().when(clientService).deleteClient(clientId);

    // When
    mockMvc.perform(delete("/api/clients/{id}", clientId))
        .andExpect(status().isNoContent());

    // Then
    verify(clientService, times(1)).deleteClient(clientId);
  }

}