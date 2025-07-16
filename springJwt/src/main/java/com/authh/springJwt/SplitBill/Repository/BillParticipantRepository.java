package com.authh.springJwt.SplitBill.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springJwt.SplitBill.Model.BillParticipant;

@Repository
public interface BillParticipantRepository extends JpaRepository<BillParticipant, Long> {
    List<BillParticipant> findByUser_Id(Long userId);
    Optional<BillParticipant> findByBill_IdAndUser_Id(Long billId, Long userId);
}
