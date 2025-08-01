package com.authh.springJwt.Admin.Controller;


import com.authh.springJwt.Admin.Service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.authh.springJwt.Utils.ResponseClass.ApiResponse;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get-transaction-histories")
    public ResponseEntity<?> getAllTransactionHistory(@RequestParam(value = "time", required = false) String time,
                                                      @RequestParam(value="searchKeyword",required = false) String searchKeyword
                                                     ){
        var transactions = adminService.getAllTransaction();
        ApiResponse<?> response = new ApiResponse<>(200, "Transaction histories fetched successfully", transactions);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllTransactionUsers(@RequestParam(value="searchKeyword",required = false) String searchKeyword){
        var users = adminService.getAllUsers();
        ApiResponse<?> response = new ApiResponse<>(200, "User list fetched successfully", users);
        return ResponseEntity.ok(response);
    }
}
