package com.example.testbusticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testbusticket.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Client findClientById(Long id);
}