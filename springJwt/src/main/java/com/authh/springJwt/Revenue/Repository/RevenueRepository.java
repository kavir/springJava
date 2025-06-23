package com.authh.springJwt.Revenue.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authh.springJwt.Revenue.Model.RevenueModel;

@Repository
public interface RevenueRepository extends JpaRepository <RevenueModel,Long> {
    
}
