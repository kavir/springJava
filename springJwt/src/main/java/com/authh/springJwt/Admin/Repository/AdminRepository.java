package com.authh.springJwt.Admin.Repository;


import com.authh.springJwt.Admin.DTO.TransactionAdminDTO;
import com.authh.springJwt.Wallet.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminRepository extends JpaRepository<Transaction, Long>{
    @Query("SELECT new com.authh.springJwt.Admin.DTO.TransactionAdminDTO(t.id," +
            " t.sender.firstname,t.sender.lastname,t.sender.number, t.receiver.firstname," +
            "t.receiver.lastname,t.receiver.number,  t.amount,t.timestamp, t.status,t.statement) FROM Transaction t")
    List<TransactionAdminDTO> findAllTransactionsForAdmin();

}
