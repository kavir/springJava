package com.authh.springJwt.Wallet.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Wallet.Model.Transaction;
import com.authh.springJwt.Wallet.Repository.TransactionRepository;
import com.authh.springJwt.Wallet.Response.TransactionDTO;

@RestController
@RequestMapping("/api/transactions")
public class GetTransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "7D") String duration,
            @RequestParam(required = false) String startDate,  
            @RequestParam(required = false) String endDate) { 
        
        System.out.println("THE USER DURATION IS: " + duration);
        System.out.println("startdate is :"+startDate);
        System.out.println("enddate is :"+ endDate);
       
       if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            System.out.println("Raw startDate: " + startDate);
            System.out.println("Raw endDate: " + endDate);
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd HH:mm:ss")
                        .optionalStart()
                        .appendFraction(java.time.temporal.ChronoField.NANO_OF_SECOND, 0, 6, true)
                        .optionalEnd()
                        .toFormatter();

                LocalDateTime customStart = LocalDateTime.parse(startDate, formatter);
                LocalDateTime customEnd = LocalDateTime.parse(endDate, formatter);

                System.out.println("Parsed Start Date: " + customStart);
                System.out.println("Parsed End Date: " + customEnd);
                return getTransactionsByCustomDateRange(userId, customStart, customEnd);
            } catch (Exception e) {
                System.out.println("Error parsing dates: " + e.getMessage());
                return ResponseEntity.badRequest().body(null);
            }
        }

        
        LocalDateTime calculatedStartDate = calculateStartDate(duration);
        System.out.println("THE CALCULATED START DATE IS: " + calculatedStartDate);
        if (calculatedStartDate != null) {
            return getTransactionsByDuration(userId, calculatedStartDate);
        } else {
            return getTransactions(userId); 
        }
    }
    
    private LocalDateTime calculateStartDate(String duration) {
        LocalDateTime now = LocalDateTime.now();
        switch (duration) {
            case "7D":
                return now.minusDays(7);
            case "14D":
                return now.minusDays(14);
            case "30D":
                return now.minusDays(30);
            case "ALL":
                return null; 
            default:
                System.out.println("Unknown duration: " + duration);
                return null; 
        }
    }
    
    private ResponseEntity<List<TransactionDTO>> getTransactionsByDuration(Long userId, LocalDateTime startDate) {
        List<Transaction> transactions = transactionRepository.findBySender_IdOrReceiver_IdAndTimestampAfter(userId, userId, startDate);
        List<TransactionDTO> transactionDTOs = convertTransactionsToDTO(transactions, userId);
        System.out.println("THE CUSTOM TRANSACTIONS ARE:1 " + transactionDTOs);
        return ResponseEntity.ok(transactionDTOs);
    }
    
   private ResponseEntity<List<TransactionDTO>> getTransactionsByCustomDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
    // Convert to LocalDate to ignore time
    LocalDate startLocalDate = startDate.toLocalDate();
    LocalDate endLocalDate = endDate.toLocalDate();

    List<Transaction> transactions = transactionRepository.findBySender_IdOrReceiver_Id(userId, userId)
        .stream()
        .filter(transaction -> {
            LocalDate transactionDate = transaction.getTimestamp().toLocalDate();
            return !transactionDate.isBefore(startLocalDate) && !transactionDate.isAfter(endLocalDate);
        })
        .collect(Collectors.toList());

    List<TransactionDTO> transactionDTOs = convertTransactionsToDTO(transactions, userId);
    System.out.println("THE CUSTOM TRANSACTIONS ARE:2 " + transactionDTOs);
    return ResponseEntity.ok(transactionDTOs);
}

    private ResponseEntity<List<TransactionDTO>> getTransactions(Long userId) {
        List<Transaction> transactions = transactionRepository.findBySender_IdOrReceiver_Id(userId, userId);
        List<TransactionDTO> transactionDTOs = convertTransactionsToDTO(transactions, userId);
        System.out.println("THE CUSTOM TRANSACTIONS ARE:3 " + transactionDTOs);
        return ResponseEntity.ok(transactionDTOs);
    }
    
    private List<TransactionDTO> convertTransactionsToDTO(List<Transaction> transactions, Long userId) {
        return transactions.stream()
                .map(transaction -> {
                    String senderName = transaction.getSender().getFirstname() + " " + transaction.getSender().getLastname();
                    String receiverName = transaction.getReceiver().getFirstname() + " " + transaction.getReceiver().getLastname();
                    String senderPhone = transaction.getSender().getNumber();
                    String receiverPhone = transaction.getReceiver().getNumber();
                    boolean isSender = Long.valueOf(transaction.getSender().getId()).equals(userId);
                    return new TransactionDTO(
                        transaction.getId(),
                        isSender ? senderName : receiverName,
                        isSender ? receiverName : senderName,
                        transaction.getAmount(),
                        transaction.getTimestamp(),
                        transaction.getStatement(),
                        transaction.getStatus(),
                        isSender ? "Sent" : "Received",
                        isSender ? receiverPhone : senderPhone
                    );
                })
                .collect(Collectors.toList());
    }
}
