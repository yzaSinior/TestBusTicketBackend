package com.example.testbusticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testbusticket.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {


}

