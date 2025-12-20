package com.authh.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springjwt.model.Bill;


@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {}

