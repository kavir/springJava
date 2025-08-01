package com.authh.springJwt.Admin.Repository;


import com.authh.springJwt.Admin.DTO.TransactionAdminDTO;
import com.authh.springJwt.Admin.DTO.UserAdminDTO;
import com.authh.springJwt.Authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminUserRepository extends JpaRepository<User, Long>{
    @Query("""
    SELECT new com.authh.springJwt.Admin.DTO.UserAdminDTO(
        u.id, u.firstname, u.lastname, u.username, u.number, u.profilePicture,
        r.rewardPoints, u.role
    )
    FROM User u
    LEFT JOIN u.reward r
    """)
    List<UserAdminDTO> findAllUsersForAdmin();

}
