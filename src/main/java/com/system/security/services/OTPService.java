package com.system.security.services;


import com.system.models.OTP;
import com.system.models.User;
import com.system.payload.response.MessageResponse;
import com.system.repository.OTPRepository;
import com.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {
    private OTPRepository otpRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ResetPassword resetPassword;

    @Autowired
    public OTPService(OTPRepository otpRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ResetPassword resetPassword) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.resetPassword = resetPassword;
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return otp;
    }
/*
    public ResponseEntity<?> generateOPTSendEmail(Long userId, String email) {
        try {
            if (otpRepository.existsByUserId(userId)) {
                OTP otp = otpRepository.findByUserId(userId);
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(otp.getUser());
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
                return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
            } else {
                User user = userRepository.findById(userId).get();
                OTP otp = new OTP();
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(user);
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
                return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }*/


    public ResponseEntity<?> checkOTPAvailable(Integer otpNumber) {
        try {
            if (otpRepository.existsByOtpNumber(otpNumber)) {
                OTP otp = otpRepository.findByOtpNumber(otpNumber);
                Date today = Calendar.getInstance().getTime();
                System.out.println(today);
                if ((otp.getExpiryDate()).compareTo(today) > 0) {
                    return ResponseEntity.ok().body(new MessageResponse("Success: Valid OTP"));
                } else {
                    return ResponseEntity.badRequest().body("expired");
                }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Please generate a new one"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> resetPassword(String password, Integer otpNumber) {
        try {
            OTP otp = otpRepository.findByOtpNumber(otpNumber);
            User user = userRepository.findById(otp.getUser().getId()).get();
            System.out.println(user.getEmail());
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("Success: Password successfully updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
///////////////////////
    ////
    ///////////////////////
    public ResponseEntity<?> sendOTPEmail(String email) {

        try {
            Optional<User> u = userRepository.findByEmail(email);
            Long userId=u.get().getId();
            if (otpRepository.existsByUserId(userId)) {
                OTP otp = otpRepository.findByUserId(userId);
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(otp.getUser());
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
            } else {
                User user = userRepository.findById(userId).get();
                OTP otp = new OTP();
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(user);
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
            }
            return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    public ResponseEntity<?> resetPasswordWithOTP(String password, Integer otp) {
        try {
            OTP u=otpRepository.findByOtpNumber(otp);
            Long un=u.getUser().getId();
            User user = userRepository.findById(un).get();
            System.out.println(u);

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("Success: Password successfully updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
}
