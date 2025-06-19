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

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.service.UserDetailServiceImp;
import com.authh.springJwt.Wallet.Model.TransferResponse;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Response.UserUpdateRequest;
import com.authh.springJwt.Wallet.Response.UserWalletResponse;
import com.authh.springJwt.Wallet.Response.WalletTransferResult;
import com.authh.springJwt.Wallet.Service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> fundTransfer(@RequestParam String senderNumber,
                                                         @RequestParam String receiverNumber,
                                                         @RequestParam Double amount,
                                                         @RequestParam String mpin) throws IOException {
        System.out.println("THE DATA ARE: " + senderNumber + " " + receiverNumber + " " + amount);
    
        if (senderNumber.equals(receiverNumber)) {
            TransferResponse selfTransferResponse = new TransferResponse(
                    "failure",
                    "Sender and receiver cannot be the same.",
                    0.0,
                    amount,
                    receiverNumber,
                    receiverNumber
            );
            return ResponseEntity.badRequest().body(selfTransferResponse);
        }
    
        WalletTransferResult result = walletService.transferFunds(senderNumber, receiverNumber, amount, mpin);
        String status = result.getStatus();
        double serviceChargeAmount = result.getServiceChargeAmount();
        double actualAmount = result.getAmount();
        String receiverName = walletService.getReceiverName(receiverNumber);
    
        TransferResponse response = new TransferResponse(
                status.equals("SUCCESS") ? "success" : "failure",
                switch (status) {
                    case "INVALID_MPIN" -> "Invalid mPIN";
                    case "INSUFFICIENT_BALANCE" -> "Insufficient balance";
                    case "SUCCESS" -> "Transfer completed successfully";
                    default -> "Transfer failed";
                },
                serviceChargeAmount,
                actualAmount,
                receiverName,
                receiverNumber
        );
    
        return switch (status) {
            case "SUCCESS" -> ResponseEntity.ok(response);
            case "INVALID_MPIN" -> ResponseEntity.status(401).body(response);
            case "INSUFFICIENT_BALANCE" -> ResponseEntity.badRequest().body(response);
            default -> ResponseEntity.status(500).body(response);
        };
    }
    

   

    @GetMapping("/userWallet")
    public ResponseEntity<UserWalletResponse> getUserWallet(@RequestParam String number) {
        Wallet wallet = walletService.getWalletByUserNumber(number);
        
        if (wallet == null) {
            return ResponseEntity.status(404).body(null);  
        }
    
        User user = wallet.getUser();
        Long userId=user.getId();
        String userName = user.getFirstname() + " " + user.getLastname();
        String firstName = user.getFirstname() ;
        String lastName =  user.getLastname();
        String userPhoneNumber = user.getNumber();
        String userProfile= user.getProfilePicture() != null ? user.getProfilePicture() : "default.png"; 
        Double walletBalance = wallet.getBalance();
    
        UserWalletResponse response = new UserWalletResponse(userId,userName, userPhoneNumber,userProfile, walletBalance,firstName,lastName);
    
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        try {
            // Call the service method to update user, no need to assign if not used
            userDetailServiceImp.updateUser(request);
            
            return ResponseEntity.ok("User updated successfully");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role provided");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    //



    
}
