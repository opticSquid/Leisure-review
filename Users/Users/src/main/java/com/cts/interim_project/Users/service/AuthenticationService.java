package com.cts.interim_project.Users.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Users.config.JwtService;
import com.cts.interim_project.Users.controller.auth.AuthenticationRequest;
import com.cts.interim_project.Users.controller.auth.AuthenticationResponse;
import com.cts.interim_project.Users.controller.auth.RegisterRequest;
import com.cts.interim_project.Users.entity.Role;
import com.cts.interim_project.Users.entity.User;
import com.cts.interim_project.Users.exceptions.UserNotFoundException;
import com.cts.interim_project.Users.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = repo.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("user with given email not found"));
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
