package com.project.tiketkonser.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Concerts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String concertName;
	private String concertArtist;
	private String concertLocation;
	private String concertImage;
	private String concertDate;
	private String category;
	private int vvipPrice;
	private int vvipCapacity;
	private int vipPrice;
	private int vipCapacity;
	private int regularPrice;
	private int regularCapacity;
	
	
	public int getVvipPrice() {
		return vvipPrice;
	}
	public void setVvipPrice(int vvipPrice) {
		this.vvipPrice = vvipPrice;
	}
	public int getVvipCapacity() {
		return vvipCapacity;
	}
	public void setVvipCapacity(int vvipCapacity) {
		this.vvipCapacity = vvipCapacity;
	}
	public int getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(int vipPrice) {
		this.vipPrice = vipPrice;
	}
	public int getVipCapacity() {
		return vipCapacity;
	}
	public void setVipCapacity(int vipCapacity) {
		this.vipCapacity = vipCapacity;
	}
	public int getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(int regularPrice) {
		this.regularPrice = regularPrice;
	}
	public int getRegularCapacity() {
		return regularCapacity;
	}
	public void setRegularCapacity(int regularCapacity) {
		this.regularCapacity = regularCapacity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConcertName() {
		return concertName;
	}
	public void setConcertName(String concertName) {
		this.concertName = concertName;
	}
	public String getConcertArtist() {
		return concertArtist;
	}
	public void setConcertArtist(String concertArtist) {
		this.concertArtist = concertArtist;
	}
	public String getConcertLocation() {
		return concertLocation;
	}
	public void setConcertLocation(String concertLocation) {
		this.concertLocation = concertLocation;
	}
	public String getConcertImage() {
		return concertImage;
	}
	public void setConcertImage(String concertImage) {
		this.concertImage = concertImage;
	}

	
	public String getConcertDate() {
		return concertDate;
	}
	public void setConcertDate(String concertDate) {
		this.concertDate = concertDate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
