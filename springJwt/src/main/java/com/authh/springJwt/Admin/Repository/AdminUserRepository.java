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
    WHERE :searchKeyword IS NULL OR (
        LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR
        LOWER(u.lastname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR
        LOWER(u.username) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR
        u.number LIKE CONCAT('%', :searchKeyword, '%') OR
        LOWER(CAST(u.role AS string)) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR
        LOWER(CAST(r.rewardPoints AS string)) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))
    )
""")
    List<UserAdminDTO> findAllUsersForAdmin(String searchKeyword);

}
