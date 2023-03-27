package com.example.testbusticket.service;

import java.util.List;

import com.example.testbusticket.exception.ClientNotFoundException;
import com.example.testbusticket.model.Client;

public interface ClientService {
  List<Client> getAllClients();
  Client getClientById(Long id) throws ClientNotFoundException;
  Client addClient(Client client);
  Client updateClient(Long id, Client clientDetails) throws ClientNotFoundException;
  void deleteClient(Long id) throws ClientNotFoundException;
}
