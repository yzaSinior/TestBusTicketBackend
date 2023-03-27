package com.example.testbusticket.service;

import java.math.BigDecimal;

public interface PaymentService {

  Boolean pay(BigDecimal amount);

}
