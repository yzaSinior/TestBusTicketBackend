package com.example.testbusticket.exception;

/**
 * This exception extends RuntimeException and takes a Long id as a parameter in its constructor.
 * It then uses this id to generate an error message indicating that a client with the specified id was not found.
 */
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException(Long id) {
    super("Client with id " + id + " not found.");
  }

}
