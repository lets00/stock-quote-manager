package com.inatel.stockquotemanager.models;

import java.util.Dictionary;
import java.util.Hashtable;

public class StockQuote {
	private String market;
	private Dictionary<String, Integer> quotes;
	
	public StockQuote() {
		quotes = new Hashtable<String, Integer>();
	}
	
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public Dictionary<String, Integer> getQuotes() {
		return quotes;
	}
	public void setQuotes(Dictionary<String, Integer> quotes) {
		this.quotes = quotes;
	}
	
}
