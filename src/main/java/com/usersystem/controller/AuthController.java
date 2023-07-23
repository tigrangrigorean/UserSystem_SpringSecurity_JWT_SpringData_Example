package com.usersystem.controller;

import com.usersystem.controller.security.JwtTokenUtils;
import com.usersystem.model.RequestDto;
import com.usersystem.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public String createAuthToken(@RequestBody RequestDto requestDto) {
        UserDetails userDetails = userService.loadUserByUsername(requestDto.getName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), requestDto.getPassword()));
        String token = jwtTokenUtils.generateToken(userDetails);
        return token;
    }

    @GetMapping("/profile/logout")
    public String logout() {
        return "redirect:/logout";
    }
}