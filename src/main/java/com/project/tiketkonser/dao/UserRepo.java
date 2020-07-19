package com.project.tiketkonser.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.tiketkonser.entity.Users;

public interface UserRepo extends JpaRepository <Users, Integer> {

	public Optional<Users> findByUsername(String username);
	
	 @Query(value = "SELECT * FROM User WHERE username = ?1" , nativeQuery = true)
	public Iterable<Users> findUsername(String username);
	 
	 public Optional<Users> findByEmail(String email);

}
