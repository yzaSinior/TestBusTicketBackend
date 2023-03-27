package com.example.testbusticket.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testbusticket.exception.ClientNotFoundException;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.repository.ClientRepository;
import com.example.testbusticket.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @Override
  public Client getClientById(Long id) throws ClientNotFoundException {
		return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
  }

  @Override
  public Client addClient(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public Client updateClient(Long id, Client clientDetails) throws ClientNotFoundException {
    Optional<Client> optionalClient = clientRepository.findById(id);
    if (optionalClient.isPresent()) {
      Client client = optionalClient.get();
      client.setEmail(clientDetails.getEmail());
      client.setName(clientDetails.getName());
      return clientRepository.save(client);
    } else {
      throw new ClientNotFoundException(id);
    }
  }

  @Override
  public void deleteClient(Long id) throws ClientNotFoundException {
    Optional<Client> optionalClient = clientRepository.findById(id);
    if (optionalClient.isPresent()) {
      clientRepository.deleteById(id);
    } else {
      throw new ClientNotFoundException(id);
    }
  }
}