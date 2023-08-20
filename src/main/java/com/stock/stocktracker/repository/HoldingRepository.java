package com.stock.stocktracker.repository;

import com.stock.stocktracker.entity.Holding;
import com.stock.stocktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByUser(User user);
    Holding findByUserAndStockSymbol(User user, String stockSymbol);
    // Add custom queries if needed
}
