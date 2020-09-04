package com.inatel.stockquotemanager.controller;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inatel.stockquotemanager.models.Quotes;
import com.inatel.stockquotemanager.models.Stock;
import com.inatel.stockquotemanager.models.StockQuote;
import com.inatel.stockquotemanager.repository.QuoteRepository;

@RestController
public class StockQuoteController {
	
	private Stock[] stock_cache;
	private RestTemplate restTemplate;
	
	@Autowired
	QuoteRepository quoteRepository;
	
	public StockQuoteController(){
		this.restTemplate = new RestTemplate();
		stock_cache = consumeStockManager();
		register();
	}
	
	private Stock[] consumeStockManager() {
		ResponseEntity<Stock[]> response = this.restTemplate.getForEntity("http://localhost:8080/stock", Stock[].class);
		return response.getBody();			
	}
	
	private void createMarketId(String market_id) {
		Dictionary<String, String> market = new Hashtable<String, String>();
		market.put("id",market_id);
		market.put("description","test "+ market_id);
		restTemplate.postForObject("http://localhost:8080/stock", market, ResponseEntity.class);
	}
	
	private void register() {
		Dictionary<String, String> market = new Hashtable<String, String>();
		market.put("host", "localhost");
		market.put("port", "8001");
		restTemplate.postForObject("http://localhost:8080/notification", market, ResponseEntity.class);
	}
	
	@GetMapping("/stock-quotes")
	public List<StockQuote> getStockQuotes() {
		List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
		if (stock_cache == null) {
			stock_cache = consumeStockManager();
		}
		for (int x = 0; x < stock_cache.length; x++) {
			StockQuote sq = new StockQuote();
			sq.setId(stock_cache[x].getId());
			sq.setQuotes(getQuotesByMarket(stock_cache[x].getId()));
			stockQuotes.add(sq);		
		}
		return stockQuotes;
	}
				
	@GetMapping("/stock-quotes/{id}")
	public StockQuote getStockQuotesById(@PathVariable(value="id") String id) {
		StockQuote stockQuote = new StockQuote();
		stockQuote.setId(id);
		stockQuote.setQuotes(getQuotesByMarket(id));
		return stockQuote;
	}

	@PostMapping("/stock-quotes")
	public Quotes createMarket(@RequestBody Quotes quotes) {
		String id = quotes.getMarketId();
		if (!isInCache(id)) {
			createMarketId(id);			
			stock_cache = null;
		}		
		return quoteRepository.save(quotes);
	}
	
	@DeleteMapping("/stockcache")
	public void cleanCache() {
		stock_cache = null;
	}
	
	private boolean isInCache(String market_id) {
		if (stock_cache == null) {
			stock_cache = consumeStockManager();
		}
		for (int x = 0; x < stock_cache.length; x++) {
			if (stock_cache[x].getId().equals(market_id)) {
				return true;
			}
		}
		return false;
	}
		
	private Dictionary<String, Integer> getQuotesByMarket(String market_id){
		List<Quotes> quotes = quoteRepository.findByMarketId(market_id);
		Dictionary<String, Integer> specific_quote = new Hashtable<String, Integer>();
		
		for (int i = 0; i < quotes.size(); i++) {
			specific_quote.put(quotes.get(i).getDate(), quotes.get(i).getQuote());
		}
		return specific_quote;
	}

}
