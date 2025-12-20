package com.authh.springjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springjwt.model.BillParticipant;

@Repository
public interface BillParticipantRepository extends JpaRepository<BillParticipant, Long> {
    List<BillParticipant> findByUser_Id(Long userId);


    Optional<BillParticipant> findByBill_IdAndUser_Id(Long billId, Long userId);
    
}


//splitbill ko changes done 