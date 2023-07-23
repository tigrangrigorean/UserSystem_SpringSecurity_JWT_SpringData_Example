package com.usersystem.controller.security;
	

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


	@Component
	public class JwtTokenUtils {
		
		private String secret = "randomsecretkey";
		
		
		public String generateToken(UserDetails userDetails) {
			Map<String, Object> claims = new HashMap<>();
			List<String> rolesList = userDetails.getAuthorities()
					.stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			
			claims.put("roles", rolesList);
			
			Date issuedDate = new Date();
			Date expiredDate = new Date(issuedDate.getTime() + 30000000);
			return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(issuedDate)
					.setExpiration(expiredDate)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		}
		
		public String getName(String token) {
			return getAllClaimsFromToken(token).getSubject();
		}
		
		public List<String> getRoles(String token) {
			return getAllClaimsFromToken(token).get("roles", List.class);
		}
		
		
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();

	}

}
