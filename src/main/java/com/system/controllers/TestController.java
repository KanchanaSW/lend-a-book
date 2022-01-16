package com.system.controllers;

import com.system.Integration.CSV.Reader;
import com.system.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/content")
public class TestController {




	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
/*
	@GetMapping("/bronze")
	@PreAuthorize("hasRole('BRONZE')or hasRole('ADMIN')")
	public String moderatorAccess() {
		return "Bronze Board.";
	}

	@GetMapping("/silver")
	@PreAuthorize("hasRole('SILVER')or hasRole('ADMIN')")
	public String silverAccess() {
		return "Silver Board.";
	}

	@GetMapping("/gold")
	@PreAuthorize("hasRole('GOLD')or hasRole('ADMIN')")
	public String goldAccess() {
		return "Gold Board.";
	}

	@GetMapping("/platinum")
	@PreAuthorize("hasRole('PLATINUM')or hasRole('ADMIN')")
	public String platinumAccess() {
		return "Platinum Board.";
	}*/
}
