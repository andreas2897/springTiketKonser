package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tiketkonser.entity.TransactionDetail;

public interface TransactionDetailRepo extends JpaRepository <TransactionDetail, Integer> {

}
