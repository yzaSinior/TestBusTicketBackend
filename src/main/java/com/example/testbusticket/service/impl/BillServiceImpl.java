package com.example.testbusticket.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.testbusticket.exception.BillNotFoundException;
import com.example.testbusticket.model.Bill;
import com.example.testbusticket.repository.BillRepository;
import com.example.testbusticket.repository.ClientRepository;
import com.example.testbusticket.service.BillService;

@Service
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;

  public BillServiceImpl(BillRepository billRepository) {
    this.billRepository = billRepository;
  }


  @Override
  public List<Bill> getAllBills() {
    return billRepository.findAll();
  }

  @Override
  public Bill getBillById(Long id) throws BillNotFoundException {
    return billRepository.findById(id).orElseThrow(() -> new BillNotFoundException(id));
  }

  @Override
  public Bill addBill(Bill bill) {
    return billRepository.save(bill);
  }

  @Override
  public Bill updateBill(Long id, Bill billDetails) throws BillNotFoundException {
    Bill bill = getBillById(id);
    bill.setPaymentMethod(billDetails.getPaymentMethod());
    bill.setReservation(billDetails.getReservation());
    return billRepository.save(bill);
  }

  @Override
  public void deleteBill(Long id) throws BillNotFoundException {
    Bill bill = getBillById(id);
    billRepository.delete(bill);
  }
}