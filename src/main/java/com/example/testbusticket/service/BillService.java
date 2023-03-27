package com.example.testbusticket.service;

import java.util.List;

import com.example.testbusticket.exception.BillNotFoundException;
import com.example.testbusticket.model.Bill;

public interface BillService {
  List<Bill> getAllBills();
  Bill getBillById(Long id) throws BillNotFoundException;
  Bill addBill(Bill bill);
  Bill updateBill(Long id, Bill billDetails) throws BillNotFoundException;
  void deleteBill(Long id) throws BillNotFoundException;
}
