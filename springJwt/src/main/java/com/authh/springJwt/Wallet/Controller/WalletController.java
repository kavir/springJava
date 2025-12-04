package com.authh.springJwt.Wallet.Controller;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.service.UserDetailServiceImp;
import com.authh.springJwt.Wallet.Model.TransferResponse;
import com.authh.springJwt.Wallet.Model.Wallet;
import com.authh.springJwt.Wallet.Response.UserUpdateRequest;
import com.authh.springJwt.Wallet.Response.UserWalletResponse;
import com.authh.springJwt.Wallet.Response.WalletTransferResult;
import com.authh.springJwt.Wallet.Service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    private final UserDetailServiceImp userDetailServiceImp;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> fundTransfer(@RequestParam(value = "senderNumber") String senderNumber,
                                                         @RequestParam(value = "receiverNumber") String receiverNumber,
                                                         @RequestParam(value = "amount") Double amount,
                                                         @RequestParam(value = "mpin") String mpin,
                                                         @RequestParam(value = "notes", required = false) String notes,
                                                         @RequestParam(value = "isUseReward", required = false) Boolean isUseReward
    ) throws IOException {
        System.out.println("THE DATA ARE: " + senderNumber + " " + receiverNumber + " " + amount + " " + notes + " " + isUseReward);

        if (senderNumber.equals(receiverNumber)) {
            TransferResponse selfTransferResponse = new TransferResponse(
                    "failure",
                    "Sender and receiver cannot be the same.",
                    "",
                    amount,
                    0.0,
                    0.0,
                    receiverNumber,
                    receiverNumber
            );
            return ResponseEntity.badRequest().body(selfTransferResponse);
        }

        WalletTransferResult result = walletService.transferFunds(senderNumber, receiverNumber, amount, mpin, notes, isUseReward);
        String status = result.getStatus();
        double serviceChargeAmount = result.getServiceChargeAmount();
        double discountAmount = result.getDiscountAmount();
        double actualAmount = result.getAmount();
        String receiverName = walletService.getReceiverName(receiverNumber);
        System.out.println("amount 2: " + actualAmount);
        System.out.println("serviceamount 2: " + serviceChargeAmount);

        TransferResponse response = new TransferResponse(
                status.equals("SUCCESS") ? "success" : "failure",
                switch (status) {
                    case "INVALID_MPIN" -> "Invalid mPIN";
                    case "INSUFFICIENT_BALANCE" -> "Insufficient balance";
                    case "SUCCESS" -> "Transfer completed successfully";
                    default -> "Transfer failed";
                },
                notes,
                actualAmount,
                serviceChargeAmount,
                discountAmount,
                receiverName,
                receiverNumber
        );
        System.out.println("Response: " + response.getAmount() + " " + response.getServiceChargeAmount());

        return switch (status) {
            case "SUCCESS" -> ResponseEntity.ok(response);
            case "INVALID_MPIN" -> ResponseEntity.status(401).body(response);
            case "INSUFFICIENT_BALANCE" -> ResponseEntity.badRequest().body(response);
            default -> ResponseEntity.status(500).body(response);
        };
    }

    /// ///////////////////////////////////////////////


    @GetMapping("/userWallet")
    public ResponseEntity<UserWalletResponse> getUserWallet(@RequestParam(value = "number") String number) {
        Wallet wallet = walletService.getWalletByUserNumber(number);

        if (wallet == null) {
            return ResponseEntity.status(404).body(null);
        }

        User user = wallet.getUser();
        Long userId = user.getId();
        String userName = user.getFirstname() + " " + user.getLastname();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        String userPhoneNumber = user.getNumber();
        String userProfile = user.getProfilePicture() != null ? user.getProfilePicture() : "default.png";
        Double walletBalance = wallet.getBalance();

        UserWalletResponse response = new UserWalletResponse(userId, userName, userPhoneNumber, userProfile, walletBalance, firstName, lastName);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        System.out.println("Updating user with request: " + request.getNumber());
        System.out.println("First Name: " + request.getFirstName());
        System.out.println("Last Name: " + request.getLastName());
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
