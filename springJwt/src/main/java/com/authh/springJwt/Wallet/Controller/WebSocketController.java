package com.authh.springJwt.Wallet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Service.WalletService;

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
        Long id=user.getId();
      
        Double walletBalance = wallet.getBalance();
        // Send the updated balance to the frontend
        messagingTemplate.convertAndSend("/topic/balance/" + id, walletBalance);
    }
}
////