package com.authh.springJwt.SplitBill.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springJwt.SplitBill.Model.Bill;


@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {}

