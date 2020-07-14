package com.project.tiketkonser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tiketkonser.dao.CartRepo;
import com.project.tiketkonser.dao.ConcertRepo;
import com.project.tiketkonser.dao.UserRepo;
import com.project.tiketkonser.entity.Cart;
import com.project.tiketkonser.entity.Concerts;
import com.project.tiketkonser.entity.Users;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ConcertRepo concertRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping
	public Iterable<Cart> getCart(){
		return cartRepo.findAll();
	}
	
	 @PostMapping("/addcart/{userId}/{concertId}")
	    public Cart addToCart(@RequestBody Cart cart, @PathVariable int userId, @PathVariable int concertId, @RequestParam int totalPrice ){
	        Concerts findConcert = concertRepo.findById(concertId).get();
	        Users findUser = userRepo.findById(userId).get();
	        cart.setTotalPrice(totalPrice);
	        cart.setConcerts(findConcert);
	        cart.setUsers(findUser);
	        return cartRepo.save(cart);
	    }
}
