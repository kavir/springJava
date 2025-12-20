package com.authh.springjwt.Admin.Service;

import com.authh.springjwt.dto.TransactionAdminDTO;
import com.authh.springjwt.dto.UserAdminDTO;

import java.util.List;

public interface AdminService {
    List<TransactionAdminDTO> getAllTransaction(String time, String searchKeyWord);
    List<UserAdminDTO> getAllUsers(String searchKeyword);
}
