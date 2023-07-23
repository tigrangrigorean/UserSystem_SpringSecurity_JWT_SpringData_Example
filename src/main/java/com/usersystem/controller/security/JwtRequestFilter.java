package com.usersystem.controller.security;


import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			System.out.println("1");
			try {
				System.out.println("2");
				username = jwtTokenUtils.getName(jwt);
				System.out.println("3");

			}catch(ExpiredJwtException e) {
				System.out.println("4");
				throw new RuntimeException("JWT Token is expired");
			}catch(SignatureException e) {
				throw new RuntimeException("JWT Token signature affected");
			}
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			System.out.println("5");

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					username,
					null,
					jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
					);
			System.out.println("6");

				SecurityContextHolder.getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
	
}