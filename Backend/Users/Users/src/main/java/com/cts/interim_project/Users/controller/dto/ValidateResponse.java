package com.cts.interim_project.Users.controller.dto;

import com.cts.interim_project.Users.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResponse {
	private String userId;
	private Role role;
	private String message;

	public ValidateResponse(String userId, Role role) {
		this.userId = userId;
		this.role = role;
	}
}
