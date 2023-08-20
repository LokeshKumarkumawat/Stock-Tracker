package com.stock.stocktracker.service;

import com.stock.stocktracker.config.HoldingRequest;
import com.stock.stocktracker.entity.Holding;
import com.stock.stocktracker.entity.Stock;
import com.stock.stocktracker.entity.User;
import com.stock.stocktracker.repository.HoldingRepository;
import com.stock.stocktracker.repository.UserRepository;
import com.stock.stocktracker.utils.StockInfo;
import com.stock.stocktracker.utils.StockNotFoundException;
import com.stock.stocktracker.utils.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;
    private final StockService stockService;
    private final UserService userService;
    private final UserRepository userRepository;

    public HoldingService(HoldingRepository holdingRepository, StockService stockService, UserService userService, UserRepository userRepository) {
        this.holdingRepository = holdingRepository;
        this.stockService = stockService;
        this.userService = userService;
        this.userRepository = userRepository;
    }



    public Holding addHolding(Long userId, String stockSymbol, HoldingRequest holdingRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Stock stock = stockService.getStockBySymbol(stockSymbol);

        Holding holding = new Holding(user, stock, holdingRequest.getQuantity(), holdingRequest.getAveragePrice());

        return holdingRepository.save(holding);
    }



    public List<Holding> getHoldingsByUser(User user) {
        return holdingRepository.findByUser(user);
    }

    public void updateHoldingQuantity(Holding holding, int newQuantity) {
        holding.setQuantity(newQuantity);
        holdingRepository.save(holding);
    }

    public void deleteHolding(Long userId, String stockSymbol) {
        Holding holding = getHoldingByUserIdAndStockSymbol(userId, stockSymbol);
        holdingRepository.delete(holding);
    }


    // Other holding-related methods...

    public double calculatePortfolioValue(User user) {
        List<Holding> holdings = holdingRepository.findByUser(user);
        double totalValue = 0.0;

        for (Holding holding : holdings) {
            Stock stockInfo = stockService.fetchStockInfo(holding.getStock().getSymbol());
            double stockPrice = stockInfo.getCurrentPrice();
            totalValue += stockPrice * holding.getQuantity();
        }

        return totalValue;
    }

    public double calculatePortfolioPerformance(User user) {
        List<Holding> holdings = holdingRepository.findByUser(user);
        double totalInvestment = 0.0;
        double currentValue = 0.0;

        for (Holding holding : holdings) {
            Stock stockInfo = stockService.fetchStockInfo(holding.getStock().getSymbol());
            double stockPrice = stockInfo.getCurrentPrice();
            totalInvestment += stockPrice * holding.getQuantity();
            currentValue += stockPrice * holding.getQuantity();
        }

        // Calculate portfolio performance as a percentage
        if (totalInvestment > 0) {
            return ((currentValue - totalInvestment) / totalInvestment) * 100.0;
        } else {
            return 0.0;
        }
    }

    public List<Holding> getHoldingsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return holdingRepository.findByUser(user);
    }


    public Holding getHoldingByUserIdAndStockSymbol(Long userId, String stockSymbol) {
        User user = userService.getUserById(userId);
        return holdingRepository.findByUserAndStockSymbol(user, stockSymbol);
    }

}
