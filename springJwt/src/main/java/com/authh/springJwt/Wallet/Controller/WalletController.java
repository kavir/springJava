package com.authh.springJwt.Wallet.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.authh.springJwt.Wallet.Model.TransferResponse;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Response.UserWalletResponse;
import com.authh.springJwt.Wallet.Service.WalletService;
import com.authh.springJwt.model.User;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/transfer")
public ResponseEntity<TransferResponse> transferFunds(@RequestParam String senderNumber,
                                                      @RequestParam String receiverNumber,
                                                      @RequestParam Double amount) throws IOException {
    System.out.println("THE DATA ARE: " + senderNumber + " " + receiverNumber + " " + amount);

    // Check if the sender and receiver are the same
    if (senderNumber.equals(receiverNumber)) {
        return ResponseEntity.badRequest().body(new TransferResponse(
            "failure",
            "Sender and receiver cannot be the same.",
            amount,
            receiverNumber,
            receiverNumber
        ));
    }

    String transferStatus = walletService.transferFunds(senderNumber, receiverNumber, amount);
    System.out.println("THE STATUS IS: " + transferStatus);
    
    String receiverName = walletService.getReceiverName(receiverNumber);
    
    if ("SUCCESS".equals(transferStatus)) {
        TransferResponse response = new TransferResponse(
            "success", 
            "Transfer completed successfully", 
            amount, 
            receiverName, 
            receiverNumber
        );
        return ResponseEntity.ok(response);
    } else {
        TransferResponse response = new TransferResponse(
            "failure", 
            "Transfer failed", 
            amount, 
            receiverName, 
            receiverNumber
        );
        return ResponseEntity.ok(response);
    }
}

    @GetMapping("/userWallet")
    public ResponseEntity<UserWalletResponse> getUserWallet(@RequestParam String number) {
        Wallet wallet = walletService.getWalletByUserNumber(number);
        
        if (wallet == null) {
            return ResponseEntity.status(404).body(null);  
        }
    
        User user = wallet.getUser();
        Long id=user.getId();
        String userName = user.getFirstname() + " " + user.getLastname();
        String userPhoneNumber = user.getNumber();
        Double walletBalance = wallet.getBalance();
    
        UserWalletResponse response = new UserWalletResponse(id,userName, userPhoneNumber, walletBalance);
    
        return ResponseEntity.ok(response);
    }
    
}
