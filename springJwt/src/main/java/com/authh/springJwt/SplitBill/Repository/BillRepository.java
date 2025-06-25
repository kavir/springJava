package com.authh.springJwt.SplitBill.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authh.springJwt.SplitBill.Model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {}

