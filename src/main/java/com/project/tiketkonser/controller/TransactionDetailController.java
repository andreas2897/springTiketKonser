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

import com.project.tiketkonser.dao.ConcertRepo;
import com.project.tiketkonser.dao.TransactionDetailRepo;
import com.project.tiketkonser.dao.TransactionRepo;
import com.project.tiketkonser.entity.Concerts;
import com.project.tiketkonser.entity.Transaction;
import com.project.tiketkonser.entity.TransactionDetail;

@RestController
@RequestMapping("/transactionDetail")
@CrossOrigin
public class TransactionDetailController {
	
	@Autowired
	private TransactionDetailRepo transactionDetailRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private ConcertRepo concertRepo;
	
	@GetMapping
	public Iterable<TransactionDetail> showAllTransactionDetail(){
		return transactionDetailRepo.findAll();
	}
	
	@PostMapping("/{transactionId}/{concertId}")
	public TransactionDetail addToTransactionDetail(
			@PathVariable int transactionId,
			@PathVariable int concertId,
			@RequestBody TransactionDetail transactionDetail,
			@RequestParam int priceProduct,
			@RequestParam int quantity
			) 
	{
		Transaction findTransaction = transactionRepo.findById(transactionId).get();
		Concerts findConcert = concertRepo.findById(concertId).get();
		
		
		transactionDetail.setTransaction(findTransaction);
		transactionDetail.setConcerts(findConcert);
		transactionDetail.setTotalPrice(priceProduct);
		transactionDetail.setQuantity(quantity);
		return transactionDetailRepo.save(transactionDetail);
	}
	
	
	

}
