package com.example.testbusticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.testbusticket.exception.ClientNotFoundException;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  // Get all clients
  @GetMapping
  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  // Get a client by id
  @GetMapping("/{id}")
  public ResponseEntity<Client> getClientById(@PathVariable Long id) {
    try {
      Client client = clientService.getClientById(id);
      return ResponseEntity.ok(client);
    } catch (ClientNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Add a new client
  @PostMapping
  public ResponseEntity<Client> addClient(@RequestBody Client client) {
    Client newClient = clientService.addClient(client);
    return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
  }

  // Update an existing client
  @PutMapping("/{id}")
  public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
    try {
      Client updatedClient = clientService.updateClient(id, client);
      return ResponseEntity.ok(updatedClient);
    } catch (ClientNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Delete a client by id
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    try {
      clientService.deleteClient(id);
      return ResponseEntity.noContent().build();
    } catch (ClientNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}