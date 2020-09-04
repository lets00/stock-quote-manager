package com.inatel.stockquotemanager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QUOTES")
public class Quotes {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private long market_id;	
	private String date;	
	private int quote;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMarket_id() {
		return market_id;
	}
	public void setMarket_id(long market_id) {
		this.market_id = market_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getQuote() {
		return quote;
	}
	public void setQuote(int quote) {
		this.quote = quote;
	}	
		
}
