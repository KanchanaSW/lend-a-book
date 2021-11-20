package com.system.controllers;

import com.system.models.User;
import com.system.security.services.OTPService;
import com.system.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
@RestController
public class    UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OTPService otpService;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<?> getAUser(@PathVariable Long id) {
        User user= userService.findUser(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User userDTO) {
        User user= userService.updateUser(userDTO);
        return ResponseEntity.ok(user);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //------------------------------Password reset-------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/otp/request-password-change")
    public ResponseEntity<?> generateOPTSendEmail(@RequestParam String email) {
        return otpService.sendOTPEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/otp/valid-check")
    public ResponseEntity<?> checkOTPAvailable(@RequestParam Integer otpNumber) {
        return otpService.checkOTPAvailable(otpNumber);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/otp/reset/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        return otpService.resetPassword(newPassword, id);
    }




}
