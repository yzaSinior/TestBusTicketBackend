package com.example.testbusticket.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import com.example.testbusticket.service.PaymentService;

@Service
public class CreditCardService implements PaymentService {

  @Override
  public Boolean pay(BigDecimal amount) {
    return true;
  }
}
