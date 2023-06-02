package com.cts.interim_project.Users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.interim_project.Users.entity.User;

public interface UserRepo extends JpaRepository<User, String> {
//	@Query("select u from User u where u.email=?1")
	Optional<User> findByEmail(String email);
}
