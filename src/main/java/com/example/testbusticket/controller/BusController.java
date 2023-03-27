package com.example.testbusticket.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.testbusticket.exception.BusNotFoundException;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.service.BusService;

@RestController
@RequestMapping("/api/buses")
public class BusController {

  @Autowired
  private BusService busService;

  // Get all buses
  @GetMapping
  public List<Bus> getAllBuses() {
    return busService.getAllBuses();
  }

  // Get a specific bus by ID
  @GetMapping("/{id}")
  public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
    try {
      Bus bus = busService.getBusById(id);
      return ResponseEntity.ok().body(bus);
    } catch (BusNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Create a new bus
  @PostMapping
  public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
    Bus newBus = busService.addBus(bus);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBus.getId()).toUri();
    return ResponseEntity.created(uri).body(newBus);
  }

  // Update an existing bus
  @PutMapping("/{id}")
  public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus busDetails) {
    try {
      Bus updatedBus = busService.updateBus(id, busDetails);
      return ResponseEntity.ok().body(updatedBus);
    } catch (BusNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Delete an existing bus
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
    try {
      busService.deleteBus(id);
      return ResponseEntity.noContent().build();
    } catch (BusNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}