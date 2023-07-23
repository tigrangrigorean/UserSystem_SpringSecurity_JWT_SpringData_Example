package com.usersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usersystem.model.User;
import com.usersystem.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/get/{id}")
	public String getById(@PathVariable long id) {
		return userService.getById(id).toString();
	}
	
	@GetMapping
	public String getAll() {
		return userService.getAll().toString();
	}
	
	@PostMapping("/save")
	public String save(@RequestBody User user) {
		return userService.save(user).toString();
	}
	
	@PutMapping("/update")
	public String update(@RequestParam long id, @RequestBody User user) {
		userService.update(id, user);
		return "User by id " + id + " updated";
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestParam long id) {
		return "User by id " + id + " deleted";
	}

}
