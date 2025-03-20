package com.authh.springJwt.Wallet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Service.WalletService;
import com.authh.springJwt.model.User;

@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // This allows us to send messages to clients

   @Autowired
    private WalletService walletService;// A service to get the updated balance
    
    @PostMapping("/transaction/completed")
    public void handleTransactionCompleted(@RequestBody String number) {
       Wallet wallet = walletService.getWalletByUserNumber(number);
        
       
    
        User user = wallet.getUser();
        int id=user.getId();
      
        Double walletBalance = wallet.getBalance();
        // Send the updated balance to the frontend
        messagingTemplate.convertAndSend("/topic/balance/" + id, walletBalance);
    }
}
