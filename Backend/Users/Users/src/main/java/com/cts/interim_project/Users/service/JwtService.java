package com.cts.interim_project.Users.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Users.entity.User;
import com.cts.interim_project.Users.exceptions.JwtNotValidException;
import com.cts.interim_project.Users.repository.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
	@Autowired
	private UserRepo userRepo;
	@Value("${jwt.secret}")
	private String secretKey = "";

	// TODO: need to understand
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String jwt) {
		// getSubject is email of user
		return extractClaim(jwt, Claims::getSubject);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // set to expire in 1 hr
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/**
	 * @param token JWT token to be validated
	 * @return user email if token validates null if it is expired or damaged
	 * @throws JwtNotValidException when the given jwt does not match with given
	 *                              signature
	 */
	public String checkIfTokenValid(String token) {
		System.out.println("Incoming token: " + token);
		try {
			final String userName = extractUsername(token);
			System.out.println("extracted email: " + userName);
			User user = userRepo.findByEmail(userName).orElse(null);
			System.out.println("user found from db: " + user);
			if (user != null && !isTokenExpired(token)) {
				return user.getEmail();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new JwtNotValidException("the given jwt string is not valid");
		}
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
