package com.inatel.stockquotemanager.models;

import java.util.Dictionary;
import java.util.Hashtable;

public class StockQuote {
	private String id;
	private Dictionary<String, Integer> quotes;
	
	public StockQuote() {
		quotes = new Hashtable<String, Integer>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String market) {
		this.id = market;
	}
	public Dictionary<String, Integer> getQuotes() {
		return quotes;
	}
	public void setQuotes(Dictionary<String, Integer> quotes) {
		this.quotes = quotes;
	}
	
}
