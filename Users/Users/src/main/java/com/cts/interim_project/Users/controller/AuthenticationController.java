package com.cts.interim_project.Users.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.interim_project.Users.controller.auth.AuthenticationRequest;
import com.cts.interim_project.Users.controller.auth.AuthenticationResponse;
import com.cts.interim_project.Users.controller.auth.RegisterRequest;
import com.cts.interim_project.Users.controller.auth.ValidateRequest;
import com.cts.interim_project.Users.controller.auth.ValidateResponse;
import com.cts.interim_project.Users.exceptions.UserNotFoundException;
import com.cts.interim_project.Users.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

	@PostMapping("/change-role")
	public ResponseEntity<Boolean> changeRole(@RequestBody String userId) {
		try {
			Boolean result = authService.changeRoleToBusinessOwner(userId);
			if (result) {
				return ResponseEntity.status(HttpStatus.SC_OK).body(result);
			} else {
				return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(result);
			}
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(false);
		}
	}

	@PostMapping("/validate")
	public ResponseEntity<ValidateResponse> validateJwt(@RequestBody ValidateRequest request) {
		ValidateResponse user = authService.validateUser(request.getToken());
		if (user.getMessage().equals("jwt expired") || user.getMessage().equals("the given jwt string is not valid")) {
			return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.SC_OK).body(user);
		}
	}
}
