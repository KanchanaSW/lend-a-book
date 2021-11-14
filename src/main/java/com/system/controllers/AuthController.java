package com.system.controllers;

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
	/*@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;*/

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.loginUserService(loginRequest);

	/*	Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getFullname(),
												 userDetails.getEmail(),
												 userDetails.getDOB(),
												 userDetails.getSignupDate(),
												 roles));*/
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User registerUser) {
		return authService.registerUserService(registerUser);

	/*	if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword())
							,signUpRequest.getFullname(),
				signUpRequest.getDOB(),
				signUpRequest.getSignupDate(),
				signUpRequest.getImage());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "bronze":
					Role bronzeRole = roleRepository.findByName(ERole.ROLE_BRONZE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(bronzeRole);
					break;
					case "silver":
						Role silverRole = roleRepository.findByName(ERole.ROLE_SILVER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(silverRole);
						break;
					case "gold":
						Role goldRole = roleRepository.findByName(ERole.ROLE_GOLD)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(goldRole);
						break;
					case "platinum":
						Role platinumRole = roleRepository.findByName(ERole.ROLE_PLATINUM)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(platinumRole);
						break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));*/
	}
}
