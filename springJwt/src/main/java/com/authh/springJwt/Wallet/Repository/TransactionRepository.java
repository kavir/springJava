package com.authh.springJwt.Wallet.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.authh.springJwt.Wallet.Model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderId(Long senderId);
    List<Transaction> findByReceiverId(Long receiverId);
    List<Transaction> findByStatus(String status);
    List<Transaction> findBySender_IdOrReceiver_Id(Long senderId, Long receiverId);
    @Query("SELECT t FROM Transaction t WHERE (t.sender.id = :userId OR t.receiver.id = :userId) AND DATE(t.timestamp) BETWEEN :startDate AND :endDate")
    List<Transaction> findBySender_IdOrReceiver_IdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<Transaction> findBySender_IdOrReceiver_IdAndTimestampBetween(Long senderId, Long receiverId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findBySender_IdOrReceiver_IdAndTimestampAfter(Long senderId, Long receiverId, LocalDateTime timestamp);
//     LocalDateTime endDate = LocalDateTime.now();
// return transactionRepository.findBySender_IdOrReceiver_IdAndTimestampBetween(userId, userId, startDate, endDate);

}
