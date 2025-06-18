package com.authh.springJwt.Wallet.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.repo.UserRepository;
import com.authh.springJwt.Reward.Model.RewardModel;
import com.authh.springJwt.Reward.Repository.RewardRepository;
import com.authh.springJwt.Wallet.Model.Transaction;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Repository.TransactionRepository;
import com.authh.springJwt.Wallet.Repository.WalletRepository;

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
    @Autowired
    private RewardRepository rewardRepository;
   

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


    @Transactional
    public String transferFunds(String senderNumber, String receiverNumber, Double amount,String mpin) throws IOException {
        User sender = userRepository.findByNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();
        if (!passwordEncoder.matches(mpin, senderWallet.getMpin())) {
            return "INVALID_MPIN";
        }
    
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

        double rewardPointsEarned = (double) (amount / 500);
        if (rewardPointsEarned > 0) {
            RewardModel reward = rewardRepository.findByUser(sender).orElse(null);
            if (reward == null) {
                reward = new RewardModel();
                reward.setUser(sender);
                reward.setRewardPoints(0);
            }

            reward.setRewardPoints(reward.getRewardPoints() + rewardPointsEarned);
            rewardRepository.save(reward);
        }

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

