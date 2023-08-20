package com.stock.stocktracker.service;



import com.stock.stocktracker.entity.Stock;
import com.stock.stocktracker.repository.StockRepository;
import com.stock.stocktracker.utils.StockNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StockService {

    private final StockRepository stockRepository;



    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.example.com/stock-info/{symbol}";




    @Autowired
    public StockService(StockRepository stockRepository, RestTemplate restTemplate) {
        this.stockRepository = stockRepository;
        this.restTemplate = restTemplate;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new StockNotFoundException("Stock not found"));
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(String symbol, Stock updatedStock) {
        Stock stock = getStockBySymbol(symbol);
        stock.setSymbol(updatedStock.getSymbol());
        stock.setName(updatedStock.getName());
        stock.setCurrentPrice(updatedStock.getCurrentPrice());
        stock.setChange(updatedStock.getChange());
        stock.setPercentChange(updatedStock.getPercentChange());
        return stockRepository.save(stock);
    }

    public void deleteStock(String symbol) {
        Stock stock = getStockBySymbol(symbol);
        stockRepository.delete(stock);
    }

    public List<Stock> getTopGainers() {
        // Implement logic to get top gaining stocks
        // For example, fetch stocks with positive change
        return stockRepository.findTopGainers();
    }

    public List<Stock> getTopLosers() {
        // Implement logic to get top losing stocks
        // For example, fetch stocks with negative change
        return stockRepository.findTopLosers();
    }






    public Stock fetchStockInfo(String symbol) {
        // Make an API request to get stock info
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("symbol", symbol);

        Stock stockInfo = restTemplate.getForObject(API_URL, Stock.class, uriVariables);

        if (stockInfo == null) {
            throw new StockNotFoundException("Stock information not available for symbol: " + symbol);
        }

        return stockInfo;
    }
}





