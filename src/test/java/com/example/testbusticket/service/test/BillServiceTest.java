package com.example.testbusticket.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.testbusticket.exception.BillNotFoundException;
import com.example.testbusticket.model.Bill;
import com.example.testbusticket.model.Client;
import com.example.testbusticket.model.Reservation;
import com.example.testbusticket.repository.BillRepository;
import com.example.testbusticket.service.impl.BillServiceImpl;
import com.example.testbusticket.util.PaymentMethod;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {

  @Mock
  private BillRepository billRepository;

  @InjectMocks
  private BillServiceImpl billService;

  @Test
  public void getAllBillsTest() {
    // Given
    List<Bill> bills = new ArrayList<>();
    bills.add(new Bill(1L, new Reservation(), new Client(), PaymentMethod.PAYPAL));
    bills.add(new Bill(2L, new Reservation(), new Client(), PaymentMethod.CREDIT_CARD));
    Mockito.when(billRepository.findAll()).thenReturn(bills);

    // When
    List<Bill> result = billService.getAllBills();

    // Then
    Assertions.assertEquals(bills, result);
  }

  @Test
  public void getBillByIdTest() throws BillNotFoundException {
    // Given
    Long billId = 1L;
    Bill bill = new Bill(billId, new Reservation(), new Client(), PaymentMethod.PAYPAL);
    Mockito.when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

    // When
    Bill result = billService.getBillById(billId);

    // Then
    Assertions.assertEquals(bill, result);
  }

  @Test
  public void addBillTest() {
    // Given
    Bill bill = new Bill(1L, new Reservation(), new Client(), PaymentMethod.PAYPAL);
    Mockito.when(billRepository.save(bill)).thenReturn(bill);

    // When
    Bill result = billService.addBill(bill);

    // Then
    Assertions.assertEquals(bill, result);
  }

  @Test
  public void updateBillTest() throws BillNotFoundException {
    // Given
    Long billId = 1L;
    Bill existingBill = new Bill(billId, new Reservation(), new Client(), PaymentMethod.PAYPAL);
    Bill updatedBill = new Bill(billId, new Reservation(), new Client(), PaymentMethod.CREDIT_CARD);
    Mockito.when(billRepository.findById(billId)).thenReturn(Optional.of(existingBill));
    Mockito.when(billRepository.save(existingBill)).thenReturn(existingBill);

    // When
    Bill result = billService.updateBill(billId, updatedBill);

    // Then
    Assertions.assertEquals(existingBill, result);
    Assertions.assertEquals(updatedBill.getPaymentMethod(), result.getPaymentMethod());
  }

  @Test
  public void deleteBillTest() throws BillNotFoundException {
    // Given
    Long billId = 1L;
    Bill bill = new Bill(billId, new Reservation(), new Client(), PaymentMethod.PAYPAL);
    Mockito.when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

    // When
    billService.deleteBill(billId);

    // Then
    Mockito.verify(billRepository, Mockito.times(1)).delete(bill);
  }

}
