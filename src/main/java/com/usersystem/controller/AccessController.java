package com.usersystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
public class AccessController {
	
	@GetMapping("/authenticated")
	public String forAuthenticatedUser() {
		return "You are authenticated user";
	}
	
	@GetMapping("/permituser")
	public String permitAllUser() {
		return "You are just user";
	}
	
	@GetMapping("/admin")
	public String adminauth() {
		return "You are admin";
	}
	
}
