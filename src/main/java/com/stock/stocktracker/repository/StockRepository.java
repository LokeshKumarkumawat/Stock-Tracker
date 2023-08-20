package com.stock.stocktracker.repository;

import com.stock.stocktracker.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);
    // Add custom queries if needed

    @Query("SELECT s FROM Stock s WHERE s.change > 0 ORDER BY s.change DESC")
    List<Stock> findTopGainers();

    @Query("SELECT s FROM Stock s WHERE s.change < 0 ORDER BY s.change ASC")
    List<Stock> findTopLosers();
}
