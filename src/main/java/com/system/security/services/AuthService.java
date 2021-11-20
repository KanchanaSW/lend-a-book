package com.system.security.services;

import com.system.models.Role;
import com.system.models.User;
import com.system.payload.request.LoginRequest;
import com.system.payload.response.JwtResponse;
import com.system.payload.response.MessageResponse;
import com.system.repository.UserRepository;
import com.system.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private RoleService roleService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthService(RoleService roleService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    public ResponseEntity<?> registerUserService(User registerUser) {
        try {
            if (userRepository.existsByEmail(registerUser.getEmail())) {
                //new MessageResponse("Username already taken!")
                return ResponseEntity.badRequest().body(new MessageResponse(("Email already taken!")));

            }else {
                //create a new user account after the checking
                User user = new User(
                        registerUser.getEmail(),
                        passwordEncoder.encode(registerUser.getPassword()),
                        registerUser.getFullname(),
                        registerUser.getDOB(),
                        registerUser.getImage(),
                        false
                );

                Role role = roleService.getRoleByName("ROLE_USER");
                user.setRole(role);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("User registered successfully"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    public ResponseEntity<?> loginUserService(LoginRequest authRequest) {
        try {
            if (!userRepository.existsByEmail(authRequest.getEmail())) {
                return ResponseEntity.ok("User name doesn't exist");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken((authentication));
            Date expiretime = jwtUtils.expirationTime();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getFullname(),
                    userDetails.getEmail(),
                    userDetails.getPassword(),
                    userDetails.isBlackListed(),
                    roles.get(0), expiretime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error")));
        }
    }
}