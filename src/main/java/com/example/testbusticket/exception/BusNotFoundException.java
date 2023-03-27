package com.example.testbusticket.exception;

/**
 * This exception class extends java.lang.Exception and takes a custom error message as a parameter for the constructor.
 * We can throw this exception whenever we encounter a scenario where we cannot find a Bus object with a given ID.
 */
public class BusNotFoundException extends Exception {
  public BusNotFoundException(Long id) {
    super("Bus with id " + id + " not found.");
  }
}
