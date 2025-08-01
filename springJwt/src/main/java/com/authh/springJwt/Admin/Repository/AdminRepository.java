package com.authh.springJwt.Admin.Repository;


import com.authh.springJwt.Admin.DTO.TransactionAdminDTO;
import com.authh.springJwt.Wallet.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AdminRepository extends JpaRepository<Transaction, Long>{
    @Query("SELECT new com.authh.springJwt.Admin.DTO.TransactionAdminDTO(" +
            "t.id, t.sender.firstname, t.sender.lastname, t.sender.number, " +
            "t.receiver.firstname, t.receiver.lastname, t.receiver.number, " +
            "t.amount, t.timestamp, t.status, t.statement) " +
            "FROM Transaction t " +
            "WHERE (:time IS NULL OR t.timestamp >= :time) " +
            "AND (" +
            ":searchKeyWord IS NULL OR " +
            "LOWER(t.sender.firstname) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "LOWER(t.sender.lastname) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "t.sender.number LIKE CONCAT('%', :searchKeyWord, '%') OR " +
            "LOWER(t.receiver.firstname) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "LOWER(t.receiver.lastname) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "t.receiver.number LIKE CONCAT('%', :searchKeyWord, '%') OR " +
            "CAST(t.amount AS string) LIKE CONCAT('%', :searchKeyWord, '%') OR " +
            "LOWER(t.status) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "LOWER(t.statement) LIKE LOWER(CONCAT('%', :searchKeyWord, '%')) OR " +
            "CAST(t.timestamp AS string) LIKE CONCAT('%', :searchKeyWord, '%')" +
            ")")

    List<TransactionAdminDTO> findAllTransactionsForAdmin(LocalDateTime time,String searchKeyWord);

}
