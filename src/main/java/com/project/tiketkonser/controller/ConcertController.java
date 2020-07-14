package com.project.tiketkonser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tiketkonser.dao.ConcertRepo;
import com.project.tiketkonser.entity.Concerts;

@RestController
@RequestMapping("/concert")
@CrossOrigin
public class ConcertController {
	
	@Autowired
	private ConcertRepo concertRepo;

	@GetMapping
	public Iterable<Concerts> getConcert(){
		return concertRepo.findAll();
	}
	
	@PostMapping
	public Concerts registerConcert(@RequestBody Concerts concert) {
		return concertRepo.save(concert);
	}
	
	@GetMapping("/{id}")
	public Concerts concertDetail(@PathVariable int id) {
		Concerts findConcerts = concertRepo.findById(id).get();
		return findConcerts;
	}
	
	@PostMapping("/{id}")
	public Concerts editConcert(@RequestBody Concerts concert) {
		return concertRepo.save(concert);
	}
	
	@DeleteMapping("/{id}")
	public void deleteConcert(@PathVariable int id) {
		concertRepo.deleteById(id);
	}
}
