package com.project.tiketkonser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tiketkonser.entity.Concerts;

public interface ConcertRepo extends JpaRepository <Concerts, Integer> {

}
