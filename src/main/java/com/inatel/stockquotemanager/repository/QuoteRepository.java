package com.inatel.stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inatel.stockquotemanager.models.Quotes;

public interface QuoteRepository extends JpaRepository<Quotes, Long>{
	
	List<Quotes> findByMarketId(String id);

}
