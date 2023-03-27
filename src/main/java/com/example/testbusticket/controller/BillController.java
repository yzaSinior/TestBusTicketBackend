package com.example.testbusticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.testbusticket.exception.BillNotFoundException;
import com.example.testbusticket.model.Bill;
import com.example.testbusticket.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

  @Autowired
  private BillService billService;

  @GetMapping
  public ResponseEntity<List<Bill>> getAllBills() {
    List<Bill> bills = billService.getAllBills();
    return new ResponseEntity<>(bills, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Bill> getBillById(@PathVariable("id") Long id) throws BillNotFoundException {
    Bill bill = billService.getBillById(id);
    return new ResponseEntity<>(bill, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Bill> addBill(@Validated @RequestBody Bill bill) {
    Bill newBill = billService.addBill(bill);
    return new ResponseEntity<>(newBill, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Bill> updateBill(@PathVariable("id") Long id, @Validated @RequestBody Bill billDetails) throws BillNotFoundException {
    Bill updatedBill = billService.updateBill(id, billDetails);
    return new ResponseEntity<>(updatedBill, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBill(@PathVariable("id") Long id) throws BillNotFoundException {
    billService.deleteBill(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
