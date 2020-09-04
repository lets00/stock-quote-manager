package com.inatel.stockquotemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inatel.stockquotemanager.models.Market;

public interface MarketRepository extends JpaRepository<Market, Long> {

}
