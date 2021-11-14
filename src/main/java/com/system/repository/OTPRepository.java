package com.system.repository;

import com.system.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Integer> {
    OTP findByOtpNumber(Integer otpNumber);

    OTP findByUserId(Long id);

    boolean existsByOtpNumber(Integer otpNumber);

    boolean existsByUserId(Long id);
}
