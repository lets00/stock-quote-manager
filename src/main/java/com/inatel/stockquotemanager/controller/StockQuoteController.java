package com.inatel.stockquotemanager.controller;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.inatel.stockquotemanager.models.Market;
import com.inatel.stockquotemanager.models.Quotes;
import com.inatel.stockquotemanager.models.StockQuote;
import com.inatel.stockquotemanager.repository.MarketRepository;
import com.inatel.stockquotemanager.repository.QuoteRepository;

@RestController
@RequestMapping(value="/api")
public class StockQuoteController {
	
	@Autowired
	MarketRepository marketRepository;
	
	@Autowired
	QuoteRepository quoteRepository;
			
	@GetMapping("/stock-quotes")
	public List<StockQuote> getStockQuotes() {
		List<Market> markets = marketRepository.findAll();
		List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
		for ( int i	= 0; i < markets.size(); i++) {
			StockQuote tempStockQuote = new StockQuote();
			tempStockQuote.setMarket(markets.get(i).getName());
			tempStockQuote.setQuotes(getQuotesByMarket(markets.get(i).getId()));			
			stockQuotes.add(tempStockQuote);
		}	
		return stockQuotes;
	}
	
	@GetMapping("/stock-quotes/{id}")
	public StockQuote getStockQuotesById(@PathVariable(value="id") long id) {
		StockQuote stockQuote = new StockQuote();
		try {
			Market market = marketRepository.findById(id);
			stockQuote.setMarket(market.getName());
			stockQuote.setQuotes(getQuotesByMarket(id));
			return stockQuote;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Market not found");
		}
	}

	@PostMapping("/stock-quotes")
	public Quotes createMarket(@RequestBody Quotes quotes) {
		return quoteRepository.save(quotes);
	}
	
	
	private Dictionary<String, Integer> getQuotesByMarket(long market_id){
		List<Quotes> quotes = quoteRepository.findByMarketId(market_id);
		Dictionary<String, Integer> specific_quote = new Hashtable<String, Integer>();
		
		for (int i = 0; i< quotes.size(); i++) {
			specific_quote.put(quotes.get(i).getDate(), quotes.get(i).getQuote());
		}
		return specific_quote;
	}

}
