package com.example.testbusticket.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.example.testbusticket.service.PaymentService;
import com.example.testbusticket.util.PaymentMethod;

@Service
public class PaymentStrategySelector {

  Map<PaymentMethod, PaymentService> paymentServices = null;

  public PaymentStrategySelector(PaypalService pService, CreditCardService ccService) {
    paymentServices = new HashMap<>();
    paymentServices.put(PaymentMethod.PAYPAL, pService);
    paymentServices.put(PaymentMethod.CREDIT_CARD, ccService);
  }

  public PaymentService select(PaymentMethod pm) {
    return paymentServices.get(pm);
  }

}