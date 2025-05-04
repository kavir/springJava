package com.authh.springJwt.Wallet.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.authh.springJwt.Wallet.Model.Transaction;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Repository.TransactionRepository;
import com.authh.springJwt.Wallet.Repository.WalletRepository;
import com.authh.springJwt.model.User;
import com.authh.springJwt.repo.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String transferFunds(String senderNumber, String receiverNumber, Double amount) throws IOException {
        User sender = userRepository.findByNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        if (senderWallet.getBalance() < amount) {
            return "Insufficient Balance!";
        }
        transactionService.processTransaction(senderNumber, receiverNumber, amount);

        // Deduct from sender and credit to receiver
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // Save transaction record
        Transaction transaction = new Transaction(sender, receiver, amount, "SUCCESS");
        transactionRepository.save(transaction);

        return "SUCCESS";
    }

    // Add the getReceiverName method to fetch the receiver's name
    public String getReceiverName(String receiverNumber) {
        User receiver = userRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        
        // Combine firstName and lastName to form the full name
        String fullName = receiver.getFirstname() + " " + receiver.getLastname();
        
        return fullName;  // Return the combined full name
    }

    public Wallet getWalletByUserNumber(String number) {
        User user = userRepository.findByNumber(number)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getWallet();  // Ensure that the wallet is not null
    }
    
    
    
}

