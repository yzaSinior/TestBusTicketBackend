package com.example.testbusticket.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.testbusticket.exception.BusNotFoundException;
import com.example.testbusticket.model.Bus;
import com.example.testbusticket.repository.BusRepository;
import com.example.testbusticket.service.BusService;

@Service
public class BusServiceImpl implements BusService {


  private BusRepository busRepository;

  public BusServiceImpl(BusRepository busRepository) {
    this.busRepository = busRepository;
  }

  @Override
  public List<Bus> getAllBuses() {
    return busRepository.findAll();
  }

  @Override
  public Bus getBusById(Long id) throws BusNotFoundException {
		return busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));
  }

  @Override
  public Bus addBus(Bus bus) {
    return busRepository.save(bus);
  }

  @Override
  public Bus updateBus(Long id, Bus busDetails) throws BusNotFoundException {
    Optional<Bus> optionalBus = busRepository.findById(id);
    if (optionalBus.isPresent()) {
      Bus bus = optionalBus.get();
      bus.setBusNumber(busDetails.getBusNumber());
      bus.setTravelDate(busDetails.getTravelDate());
      bus.setDepartureTime(busDetails.getDepartureTime());
      bus.setPrice(busDetails.getPrice());
      bus.setNumberOfSeats(busDetails.getNumberOfSeats());
      return busRepository.save(bus);
    } else {
      throw new BusNotFoundException(id);
    }
  }

  @Override
  public void deleteBus(Long id) throws BusNotFoundException {
    Optional<Bus> optionalBus = busRepository.findById(id);
    if (optionalBus.isPresent()) {
      busRepository.deleteById(id);
    } else {
      throw new BusNotFoundException(id);
    }
  }
}