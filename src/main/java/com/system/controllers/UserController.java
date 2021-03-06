package com.system.controllers;

import com.system.models.Subscription;
import com.system.models.User;
import com.system.models.MessageResponse;
import com.system.security.services.OTPService;
import com.system.security.services.SubscriptionService;
import com.system.security.services.UserDetailsImpl;
import com.system.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OTPService otpService;
    @Autowired
    private SubscriptionService subscriptionService;



    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    //search name
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/userName/{name}")
    public List<User> search(@PathVariable String name){
        if (name.equals("ALL")){
            return userService.getAllUsers();
        }else{
            return userService.searchName(name);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/name")
    public List<User> searchN(HttpServletRequest request){
        String name=request.getParameter("name");
        return userService.searchName(name);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/user/{id}")
    public ResponseEntity<?> getAUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User userDTO) {
        try {
            User user = userService.updateUser(userDTO);
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.badRequest().body("error");
        }
    }

    //update user subscription
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST, value = "/updateSubs/{subscriptionId}")
    public ResponseEntity<?> updateSubscription(@Valid @PathVariable Long subscriptionId,@RequestBody Subscription subscription) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            Long id= userDetails.getId();

           User user= userService.updateSubscription(id, subscriptionId);
            return ResponseEntity.ok().body("success");
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.badRequest().body("error");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //------------------------------Password reset-------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping(value = "/otp/request-password-change/{email}")
    public ResponseEntity<?> generateOPTSendEmail(@PathVariable String email) {
        ResponseEntity<?> check= otpService.sendOTPEmail(email);
        if (check.getStatusCodeValue() == 200){
            return ResponseEntity.ok().body("success");
        }else{
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PostMapping(value = "/otp/valid-check/{otpNumber}")
    public ResponseEntity<?> checkOTPAvailable(@PathVariable Integer otpNumber) {
        ResponseEntity<?> check=otpService.checkOTPAvailable(otpNumber);
        if (check.getStatusCodeValue()==200){
            return ResponseEntity.ok().body("success");
        }else if (check.equals("expired")){
            return ResponseEntity.badRequest().body("expired");
        }else{
            return ResponseEntity.badRequest().body("error");
        }
    }


    @PostMapping(value = "/otp/reset/{otp}/pass/{password}")
    public ResponseEntity<?> resetPassword(@PathVariable Integer otp, @PathVariable String password) {
       ResponseEntity<?> check= otpService.resetPassword(password, otp);
        if (check.getStatusCodeValue() == 200){
            return ResponseEntity.ok().body("success");
        }else{
            return ResponseEntity.badRequest().body("error");
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //--------------------------------Subscriptions-----------------------------------------------------------
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/subscription/all")
    public List<Subscription> allSubscriptions() {
        return subscriptionService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/subscription/save")
    public ResponseEntity<?> saveNew(@RequestBody Subscription subscription) {
        try {
            subscriptionService.saveSubs(subscription);
            return ResponseEntity.ok().body(new MessageResponse("Success" + subscription));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse("error" + ex));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/subscription/update")
    public ResponseEntity<?> update(@RequestBody Subscription subscription) {
        try {
            subscriptionService.updateSubs(subscription);
            return ResponseEntity.ok().body("success");
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/subscription/find/{subscriptionId}")
    public Subscription findSubs(@PathVariable Long subscriptionId) {
        try {
            return subscriptionService.getSubById(subscriptionId);
        } catch (Exception ex) {
            return null;
        }
    }
/*
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(method = RequestMethod.PUT, value = "/subscription/updateType/{subscriptionId}/user/{id}")
    public ResponseEntity<?> updateSubscriptionType(@PathVariable Long subscriptionId, @PathVariable Long id) {
        try {
            // UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // Long id= userDetails.getId();
            User user = userService.updateSubscription(id, subscriptionId);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse("error" + ex));
        }
    }*/
}
