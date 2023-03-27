package com.example.testbusticket.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.repository.ClientRepository;
import com.example.testbusticket.service.impl.ClientServiceImpl;

public class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientServiceImpl clientService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllClients() {
    // Given
    List<Client> clients = new ArrayList<>();
    clients.add(new Client("John Doe", "john@example.com"));
    clients.add(new Client("Jane Doe", "jane@example.com"));
    when(clientRepository.findAll()).thenReturn(clients);

    // When
    List<Client> result = clientService.getAllClients();

    // Then
    assertThat(result).isEqualTo(clients);
  }

  @Test
  public void testGetClientById() {
    // Given
    Client client = new Client("John Doe", "john@example.com");
    client.setId(1L);
    when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));

    // When
    Client result = clientService.getClientById(1L);

    // Then
    assertThat(result).isEqualTo(client);
  }

  @Test
  public void testAddClient() {
    // Given
    Client client = new Client("John Doe", "john@example.com");
    when(clientRepository.save(client)).thenReturn(client);

    // When
    Client result = clientService.addClient(client);

    // Then
    assertThat(result).isEqualTo(client);
  }

  @Test
  public void testUpdateClient() {
    // Given
    Client client = new Client("John Doe", "john@example.com");
    client.setId(1L);
    when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
    when(clientRepository.save(client)).thenReturn(client);

    // When
    Client updatedClient = new Client("John Doe Jr.", "johnjr@example.com");
    updatedClient.setId(1L);
    Client result = clientService.updateClient(1L, updatedClient);

    // Then
    assertThat(result.getName()).isEqualTo("John Doe Jr.");
    assertThat(result.getEmail()).isEqualTo("johnjr@example.com");
  }

  @Test
  public void testDeleteClient() {
    // Given
    Client client = new Client("John Doe", "john@example.com");
    client.setId(1L);
    when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));

    // When
    clientService.deleteClient(1L);

    // Then
    // Verify that the delete method of the clientRepository is called with the correct id
    verify(clientRepository, times(1)).deleteById(1L);
  }

}