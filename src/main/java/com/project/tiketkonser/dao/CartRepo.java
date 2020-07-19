package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.tiketkonser.entity.Cart;

public interface CartRepo extends JpaRepository <Cart, Integer> {
	
	@Query(value = "SELECT * FROM cart WHERE users_id= ?1", nativeQuery = true)
    public Iterable<Cart> findByUserId(int userId);
}
