package com.system.controllers;

import javax.validation.Valid;

import com.system.models.User;
import com.system.models.LoginRequest;
import com.system.security.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
