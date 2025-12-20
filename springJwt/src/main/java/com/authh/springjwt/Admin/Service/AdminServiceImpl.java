package com.authh.springjwt.Admin.Service;


import com.authh.springjwt.dto.TransactionAdminDTO;
import com.authh.springjwt.dto.UserAdminDTO;
import com.authh.springjwt.Admin.Repository.AdminRepository;
import com.authh.springjwt.Admin.Repository.AdminUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private AdminUserRepository adminUserRepository;

    public List<TransactionAdminDTO> getAllTransaction(String time, String searchKeyWord) {
        System.out.println("the parameter for transaction by admin are "+searchKeyWord);
        System.out.println("the parameter for transaction time by admin are "+time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = null;

        if (time != null && !time.trim().isEmpty()) {
            dateTime = LocalDateTime.parse(time, formatter);
        }

        return adminRepository.findAllTransactionsForAdmin(dateTime, searchKeyWord);
    }

    public List<UserAdminDTO> getAllUsers(String searchKeyword){
       return adminUserRepository.findAllUsersForAdmin(searchKeyword);

   }


}
