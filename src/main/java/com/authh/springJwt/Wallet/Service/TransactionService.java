package com.authh.springJwt.Wallet.Service;

import org.springframework.stereotype.Service;
import com.authh.springJwt.Wallet.WebSocket.TransactionWebSocketHandler;
import java.io.IOException;

@Service
public class TransactionService {

    public void processTransaction(String sender, String receiver, double amount) throws IOException {
        String message = "Transaction from " + sender + " to " + receiver + " of NRP " + amount + " completed!";
        System.out.println("The message in WebSocket is: " + message);  // Log the message
        TransactionWebSocketHandler.sendTransactionUpdate(message);
    }
    
}
