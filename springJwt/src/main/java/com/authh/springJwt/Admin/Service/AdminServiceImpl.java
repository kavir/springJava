package com.authh.springJwt.Admin.Service;


import com.authh.springJwt.Admin.DTO.TransactionAdminDTO;
import com.authh.springJwt.Admin.DTO.UserAdminDTO;
import com.authh.springJwt.Admin.Repository.AdminRepository;
import com.authh.springJwt.Admin.Repository.AdminUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private AdminUserRepository adminUserRepository;

   public List<TransactionAdminDTO> getAllTransaction(){
       return adminRepository.findAllTransactionsForAdmin();

   } public List<UserAdminDTO> getAllUsers(){
       return adminUserRepository.findAllUsersForAdmin();

   }


}
