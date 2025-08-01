package com.authh.springJwt.Admin.Service;

import com.authh.springJwt.Admin.DTO.TransactionAdminDTO;
import com.authh.springJwt.Admin.DTO.UserAdminDTO;

import java.util.List;

public interface AdminService {
    List<TransactionAdminDTO> getAllTransaction(String time,String searchKeyWord);
    List<UserAdminDTO> getAllUsers(String searchKeyword);
}
