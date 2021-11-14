package com.system.security.services;

import com.system.models.OTP;

public interface EmailService {
    void sendEmail(OTP otp, String email);
}
