package com.authh.springJwt.Wallet.Controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.authh.springJwt.Wallet.Model.TransferResponse;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Response.UserUpdateRequest;
import com.authh.springJwt.Wallet.Response.UserWalletResponse;
import com.authh.springJwt.Wallet.Service.WalletService;
import com.authh.springJwt.model.User;
import com.authh.springJwt.service.UserDetailServiceImp;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
   

    @PostMapping("/transfer")
public ResponseEntity<TransferResponse> transferFunds(@RequestParam String senderNumber,
                                                      @RequestParam String receiverNumber,
                                                      @RequestParam Double amount,
                                                      @RequestParam String mpin
                                                      
                                                      ) throws IOException {
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

    String transferStatus = walletService.transferFunds(senderNumber, receiverNumber, amount,mpin);
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
    } else if ("INVALID_MPIN".equals(transferStatus)) {
        return ResponseEntity.status(401).body(new TransferResponse(
            "failure",
            "Invalid mPIN",
            amount,
            receiverName,
            receiverNumber
        ));
    } else if ("INSUFFICIENT_BALANCE".equals(transferStatus)) {
        return ResponseEntity.badRequest().body(new TransferResponse(
            "failure",
            "Insufficient balance",
            amount,
            receiverName,
            receiverNumber
        ));
    } else {
        return ResponseEntity.status(500).body(new TransferResponse(
            "failure",
            "Transfer failed",
            amount,
            receiverName,
            receiverNumber
        ));
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
        String firstName = user.getFirstname() ;
        String lastName =  user.getLastname();
        String userPhoneNumber = user.getNumber();
        String userProfile= user.getProfilePicture() != null ? user.getProfilePicture() : "default.png"; 
        Double walletBalance = wallet.getBalance();
    
        UserWalletResponse response = new UserWalletResponse(id,userName, userPhoneNumber,userProfile, walletBalance,firstName,lastName);
    
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        try {
            // Call the service method to update user, no need to assign if not used
            userDetailServiceImp.updateUser(request);
            
            // Return success message
            return ResponseEntity.ok("User updated successfully");
        } catch (UsernameNotFoundException ex) {
            // User not found case
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException ex) {
            // Invalid role string parsing case
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role provided");
        } catch (Exception ex) {
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    



    
}
