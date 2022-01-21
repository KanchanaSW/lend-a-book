package com.system.security.services;

import com.system.models.OTP;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
public interface EmailService {
    void sendEmail(OTP otp, String email);
}
