package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tiketkonser.entity.Cart;

public interface CartRepo extends JpaRepository <Cart, Integer> {

}
