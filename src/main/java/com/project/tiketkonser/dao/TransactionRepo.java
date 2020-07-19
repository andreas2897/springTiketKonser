package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tiketkonser.entity.Transaction;

public interface TransactionRepo extends JpaRepository <Transaction, Integer> {

}
