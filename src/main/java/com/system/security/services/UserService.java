package com.system.security.services;

import com.system.models.OTP;
import com.system.models.Subscription;
import com.system.models.User;
import com.system.repository.OTPRepository;
import com.system.repository.RoleRepository;
import com.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private SubscriptionService subscriptionService;


    public User updateSubscription(Long id,Long subscriptionId){

        Optional<User> user = userRepository.findById(id);
        User u=user.get();
        Subscription subscription= subscriptionService.getSubById(subscriptionId);
        u.setSubscription(subscription);
        return userRepository.save(u);
    }

    public User updateUser(User userDTO){
        Optional<User> user = userRepository.findById(userDTO.getId());
        User u=user.get();

        //u.setEmail(userDTO.getEmail());
        u.setFullname(userDTO.getFullname());
        u.setDOB(userDTO.getDOB());
        u.setImage(userDTO.getImage());
        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(u);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User findUser(Long id){
        Optional<User> user = userRepository.findById(id);
        User u = null;
        if(user.isPresent()){
            u = user.get();
        }
        return u;
    }

    public User directUserType(String email){
        Optional<User> user = userRepository.findByEmail(email);
        User u = null;
        if(user.isPresent()){
            u = user.get();
        }
        return u;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<User> searchName(String name){
        return userRepository.name(name);
    }

    public void deleteByUser(User user){
        userRepository.delete(user);
    }

    public void deleteUser(Long id) {
       /* if (otpRepository.existsByUserUserId(userID)){
           OTP otp= otpRepository.findByUserUserId(userID);
           otpRepository.delete(otp);
        }*/
        Optional<User> user = userRepository.findById(id);
        User u = null;

        try {
            if (otpRepository.existsByUserId(id)) {
                OTP otp = otpRepository.findByUserId(id);
                otpRepository.delete(otp);
            }
        } finally {
            if (user.isPresent()) {
                u = user.get();
                userRepository.delete(u);
            }
        }
    }
}
