package com.inatel.stockquotemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inatel.stockquotemanager.models.Quotes;

public interface QuoteRepository extends JpaRepository<Quotes, Long>{

}
