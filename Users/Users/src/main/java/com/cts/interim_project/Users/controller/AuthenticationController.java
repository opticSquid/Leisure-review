package com.cts.interim_project.Users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.interim_project.Users.controller.auth.AuthenticationRequest;
import com.cts.interim_project.Users.controller.auth.AuthenticationResponse;
import com.cts.interim_project.Users.controller.auth.RegisterRequest;
import com.cts.interim_project.Users.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}
}
