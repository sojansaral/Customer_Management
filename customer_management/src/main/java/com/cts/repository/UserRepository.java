package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	Optional<User> findByUsernameOrEmail(String username,String email);
}
