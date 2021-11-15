package com.system.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.system.models.Role;
import com.system.models.User;
import com.system.payload.request.LoginRequest;
import com.system.payload.request.SignupRequest;
import com.system.payload.response.JwtResponse;
import com.system.payload.response.MessageResponse;
import com.system.repository.RoleRepository;
import com.system.repository.UserRepository;
import com.system.security.jwt.JwtUtils;
import com.system.security.services.AuthService;
import com.system.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	//	System.out.println(authService.loginUserService(loginRequest));
		return authService.loginUserService(loginRequest);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User registerUser) {
		return authService.registerUserService(registerUser);
	}
}
