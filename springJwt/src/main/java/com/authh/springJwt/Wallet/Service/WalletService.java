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
    // @Autowired
    // private RevenueRepository revenueRepository;
    @Autowired
    private RewardRepository rewardRepository;

    double serviceChargePercentage;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

   @Transactional
    public WalletTransferResult transferFunds(String senderNumber, String receiverNumber, Double amount, String mpin,String statement, Boolean isUseReward) throws IOException {
        double minRewardPoints = 0.00;
        double rewardToMoneyRatio = 100.0; // 100 points = Rs 1
        double discountAmount = 0.0;

        User sender = userRepository.findByNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        if (!passwordEncoder.matches(mpin, senderWallet.getMpin())) {
            return new WalletTransferResult("INVALID_MPIN", 0.0,discountAmount, amount);
        }

        // Define service charge and min reward based on amount
        if (amount <= 100) {
            serviceChargePercentage = 0.08;
        } else if (amount <= 500) {
            serviceChargePercentage = 0.09;
        } else if (amount <= 1000) {
            serviceChargePercentage = 0.10;
            minRewardPoints = 200.0;
        } else if (amount <= 5000) {
            serviceChargePercentage = 0.20;
            minRewardPoints = 500.0;
        } else if (amount <= 10000) {
            serviceChargePercentage = 0.20;
            minRewardPoints = 600.0;
        } else {
            serviceChargePercentage = 0.10;
            minRewardPoints = 500.0;
        }

        double serviceChargeAmount = (serviceChargePercentage * amount) / 100.0;
        double totalDebit = amount + serviceChargeAmount;

        // Fetch reward model
        RewardModel reward = rewardRepository.findByUser(sender).orElse(null);
        if (reward == null) {
            reward = new RewardModel();
            reward.setUser(sender);
            reward.setRewardPoints(0.0);
        }

        // Apply reward discount if requested
        if (Boolean.TRUE.equals(isUseReward)) {
            if (reward.getRewardPoints() < minRewardPoints) {
                throw new IllegalArgumentException("Reward Points not sufficient");
            }
            reward.setRewardPoints(reward.getRewardPoints() - minRewardPoints);
            discountAmount = minRewardPoints / rewardToMoneyRatio; // convert reward points to Rs
            totalDebit -= discountAmount;
        }

        // Check balance again after applying reward discount
        if (senderWallet.getBalance() < totalDebit) {
            return new WalletTransferResult("INSUFFICIENT_BALANCE", serviceChargeAmount,discountAmount, amount);
        }

        // Process transaction
        transactionService.processTransaction(senderNumber, receiverNumber, amount);

        senderWallet.setBalance(senderWallet.getBalance() - totalDebit);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // Save transaction
        Transaction transaction = new Transaction(sender, receiver, amount, statement,"SUCCESS");
        transactionRepository.save(transaction);

        // Reward earn logic
        double rewardPointsEarned = amount / 500.0;
        reward.setRewardPoints(reward.getRewardPoints() + rewardPointsEarned);
        rewardRepository.save(reward);

        System.out.println("Transferred Amount: " + amount);
        System.out.println("Service Charge: " + serviceChargeAmount);
        System.out.println("Reward Discount Applied: " + discountAmount);

        return new WalletTransferResult("SUCCESS", serviceChargeAmount,discountAmount, amount);
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

//okay vayo