package com.example.testbusticket.service;

import java.util.List;

import com.example.testbusticket.exception.BusNotFoundException;
import com.example.testbusticket.model.Bus;

public interface BusService {
  List<Bus> getAllBuses();
  Bus getBusById(Long id) throws BusNotFoundException;
  Bus addBus(Bus bus);
  Bus updateBus(Long id, Bus busDetails) throws BusNotFoundException;
  void deleteBus(Long id) throws BusNotFoundException;
}
