package com.cts.interim_project.Users.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Users.controller.dto.AuthenticationRequest;
import com.cts.interim_project.Users.controller.dto.AuthenticationResponse;
import com.cts.interim_project.Users.controller.dto.RegisterRequest;
import com.cts.interim_project.Users.controller.dto.ValidateRequest;
import com.cts.interim_project.Users.controller.dto.ValidateResponse;
import com.cts.interim_project.Users.entity.Role;
import com.cts.interim_project.Users.entity.User;
import com.cts.interim_project.Users.exceptions.EmailOrPasswordWrong;
import com.cts.interim_project.Users.exceptions.JwtNotValidException;
import com.cts.interim_project.Users.exceptions.UserNotFoundException;
import com.cts.interim_project.Users.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final UserRepo repo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder().email(request.getEmail()).name(request.getName())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
		repo.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public Boolean changeRoleToBusinessOwner(String userId) {
		User user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("user with given id not found"));
		user.setRole(Role.SERVICE_OWNER);
		try {
			repo.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		}catch(BadCredentialsException ex) {
			throw new EmailOrPasswordWrong("email or password is wrong");
		}
		User user = repo.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("user with given email not found"));
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public ValidateResponse validateUser(String token) {
		try {
			String isValidUserEmail = jwtService.checkIfTokenValid(token);
			log.error("email returned from jwtService: {}", isValidUserEmail);
			if (isValidUserEmail != null) {
				User user = repo.findByEmail(isValidUserEmail)
						.orElseThrow(() -> new UserNotFoundException("user with given email not found"));
				return new ValidateResponse(user.getUserId(), user.getRole(), "user validated");
			} else {
				return new ValidateResponse(null, null, "jwt expired or mismatched");
			}
		} catch (JwtNotValidException e) {
			return new ValidateResponse(null, null, e.getMessage());
		}
	}
}
