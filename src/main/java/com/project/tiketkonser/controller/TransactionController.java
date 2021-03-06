package com.project.tiketkonser.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.project.tiketkonser.dao.ConcertRepo;
import com.project.tiketkonser.dao.TransactionRepo;
import com.project.tiketkonser.dao.UserRepo;
import com.project.tiketkonser.entity.Concerts;
import com.project.tiketkonser.entity.Transaction;
import com.project.tiketkonser.entity.Users;
import com.project.tiketkonser.util.EmailUtil;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ConcertRepo concertRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	private String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/images/BuktiTrf/";
	private String message;
	
//	@GetMapping
//	public Iterable<Transaction> showAllTransaction(){
//		return transactionRepo.findAll();
//				}
	
	
	@PostMapping("/{userId}/{totalPrice}")
	public Transaction addToTransaction(@PathVariable int userId, @PathVariable int totalPrice, @RequestBody Transaction transaction, @RequestParam String checkoutTime ) {
		Users findUser = userRepo.findById(userId).get();
		transaction.setTotalPrice(totalPrice);
		transaction.setUsers(findUser);
		transaction.setCheckoutTime(checkoutTime);
		return transactionRepo.save(transaction);
	}
	
	@PostMapping("/uploadBuktiTransfer/{transactionId}")
	public String addBuktiTransfer(@RequestParam("file") MultipartFile file, @PathVariable int transactionId)throws JsonMappingException, JsonProcessingException{
		Transaction findTransaction = transactionRepo.findById(transactionId).get();
		
		Date date = new Date();
		
		String fileExtension = file.getContentType().split("/")[1];
		
		String newFileName = "PROD-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/download/").path(fileName).toUriString();
		findTransaction.setBuktiTrf(fileDownloadUrl);
		
		transactionRepo.save(findTransaction);
		return fileDownloadUrl;
	}
	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("DOWNLOAD");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment: filename=\"" + resource.getFilename()+ "\"").body(resource);
	}
	
	@PutMapping("/accept/{transactionId}")
	public Transaction acceptTransaction(@PathVariable int transactionId, @RequestParam String acceptTime) {
		Transaction findTransaction = transactionRepo.findById(transactionId).get();
		Users findUser = userRepo.findById(findTransaction.getUsers().getId()).get();
		
		message ="";
		findTransaction.setAcceptTime(acceptTime);
		findTransaction.setStatus("Success");
		
		message = "<h1> Terima Kasih sudah membeli  ticket di Korean Wave </h1>";
		
		findTransaction.getTransactionDetail().forEach(val ->{
//			val.getConcerts().setStokAdmin((val.getGame().getStokAdmin()- val.getQuantity()));
//			val.getConcerts().setSold(val.getQuantity());
			Concerts findConcert = concertRepo.findById(val.getConcerts().getId()).get();
			message += "<h1> Concert Name = " + val.getConcerts().getConcertName()+ "jumlah = " + val.getQuantity() + "harga concert = " + val.getTotalPrice() + "dengan kategori :" + val.getTicketType() + "</h1>";
			if(val.getTicketType() == "vvip") {
				findConcert.setVvipCapacity(findConcert.getVvipCapacity() - val.getQuantity());
				concertRepo.save(findConcert);
			}
			concertRepo.save(val.getConcerts());
		});
		
		message += "<h1> total harga = " + findTransaction.getTotalPrice() + "</h1>";
		
		this.emailUtil.sendEmail(findUser.getEmail(), "Transaction Success", message);
		return transactionRepo.save(findTransaction);
		
	}
	
	@PutMapping("/reject/{transactionId}")
	public Transaction rejectTransaction(@PathVariable int transactionId) {
		Transaction findTransaction = transactionRepo.findById(transactionId).get();
		Users findUser = userRepo.findById(findTransaction.getUsers().getId()).get();
		findTransaction.setBuktiTrf(null);
		findTransaction.setStatus("reject");
		
		this.emailUtil.sendEmail(findUser.getEmail(), "Transaction Reject", "<h1>Mohon maaf proses transaksi anda gagal mohon kirim ulang bukti transfer </h1>");
		
		return transactionRepo.save(findTransaction);
	}
	
	@GetMapping
    public Iterable<Transaction> getTransactions(@RequestParam String status){
    	return transactionRepo.findTransaksiByStatus(status);
    }
}
	


