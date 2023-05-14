package com.cts.interim_project.Users.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Users.exceptions.JwtNotValidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private String secretKey = "";

	// TODO: need to understand
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
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
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) //set to expire in 1 hr
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
	 * @param email the user email to be validated against
	 * @return true if token validates false if it is expired
	 * @throws JwtNotValidException when the given jwt does not match with given signature
	 */
	public Boolean checkIfTokenValid(String token, String email) {
		try {
			final String userName = extractUsername(token);
			return userName.equals(email) && !isTokenExpired(token);
		} catch (Exception e) {
			throw new JwtNotValidException("the given jwt string is not valid");
		}
	}

	private Boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}

}
