package com.example.testbusticket.exception;

/**
 * This exception will be thrown when a requested bill is not found in the system.
 */
public class BillNotFoundException extends RuntimeException {

  public BillNotFoundException(Long id) {
    super("Bill with id " + id + " not found.");
  }

}
