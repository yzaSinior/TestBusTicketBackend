package com.example.testbusticket.exception;

public class BillGenerationException extends RuntimeException {
  public BillGenerationException(String message) {
    super(message);
  }
}