package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.tiketkonser.entity.Transaction;

public interface TransactionRepo extends JpaRepository <Transaction, Integer> {
	@Query(value = "select * from transaction where status = ?1",nativeQuery = true)
	public Iterable<Transaction> findTransaksiByStatus(String status);

}
