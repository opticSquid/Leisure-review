package com.cts.interim_project.Users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.interim_project.Users.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
}
