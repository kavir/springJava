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
import com.authh.springJwt.Wallet.Response.WalletTransferResult;

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
    double serviceChargePercentage;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Transactional
    public WalletTransferResult transferFunds(String senderNumber, String receiverNumber, Double amount, String mpin) throws IOException {
        User sender = userRepository.findByNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        if (!passwordEncoder.matches(mpin, senderWallet.getMpin())) {
            return new WalletTransferResult("INVALID_MPIN", 0.0,amount);
        }

        if (senderWallet.getBalance() < amount) {
            return new WalletTransferResult("INSUFFICIENT_BALANCE", 0.0,amount);
        }

        transactionService.processTransaction(senderNumber, receiverNumber, amount);

        if (amount <= 100) {
            serviceChargePercentage = 0.08;
        } else if (amount <= 500) {
            serviceChargePercentage = 0.09;
        } else if (amount <= 1000) {
            serviceChargePercentage = 0.1;
        } else if (amount <= 5000) {
            serviceChargePercentage = 0.2;
        } else if (amount <= 10000) {
            serviceChargePercentage = 0.2;
        } else {
            serviceChargePercentage = 0.1;
        }

        double serviceChargeAmount = (serviceChargePercentage * amount) / 100.0;
        double totalDebit = amount + serviceChargeAmount;

        senderWallet.setBalance(senderWallet.getBalance() - totalDebit);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction(sender, receiver, amount, "SUCCESS");
        transactionRepository.save(transaction);

        double rewardPointsEarned = amount / 500.0;
        RewardModel reward = rewardRepository.findByUser(sender).orElse(null);
        if (reward == null) {
            reward = new RewardModel();
            reward.setUser(sender);
            reward.setRewardPoints(0);
        }
        reward.setRewardPoints(reward.getRewardPoints() + rewardPointsEarned);
        rewardRepository.save(reward);
        System.out.println("amount 1: " + amount);
        System.out.println("serviceamount 1: " + serviceChargeAmount);

        return new WalletTransferResult("SUCCESS", serviceChargeAmount,amount);
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

