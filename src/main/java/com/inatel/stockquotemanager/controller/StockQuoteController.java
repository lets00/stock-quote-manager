package com.inatel.stockquotemanager.controller;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		List<Quotes> quotes = quoteRepository.findAll();
		List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
		for ( int i	= 0; i < markets.size(); i++) {
			StockQuote tempStockQuote = new StockQuote();
			tempStockQuote.setMarket(markets.get(i).getName());
			for (int j = 0; j < quotes.size(); j++) {
				if (quotes.get(j).getMarket_id() == markets.get(i).getId()) {
					Dictionary<String, Integer> specific_quote = tempStockQuote.getQuotes();
					specific_quote.put(quotes.get(j).getDate(), quotes.get(j).getQuote());
					tempStockQuote.setQuotes(specific_quote);
				}
			}
			stockQuotes.add(tempStockQuote);
		}	
		return stockQuotes;
	}

	@PostMapping("/stock-quotes")
	public Quotes createMarket(@RequestBody Quotes quotes) {
		return quoteRepository.save(quotes);
	}	

}
