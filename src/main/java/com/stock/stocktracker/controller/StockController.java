package com.stock.stocktracker.controller;

import com.stock.stocktracker.entity.Stock;
import com.stock.stocktracker.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

//API FETCH
//    @GetMapping("/{symbol}")
//    public Stock getStockInfo(@PathVariable String symbol) {
//        return stockService.fetchStockInfo(symbol);
//    }

    // Other stock-related endpoints...

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol) {
        Stock stock = stockService.getStockBySymbol(symbol);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<Stock> updateStock(@PathVariable String symbol, @RequestBody Stock updatedStock) {
        Stock stock = stockService.updateStock(symbol, updatedStock);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<String> deleteStock(@PathVariable String symbol) {
        stockService.deleteStock(symbol);
        return new ResponseEntity<>("Stock deleted successfully", HttpStatus.NO_CONTENT);
    }


    @GetMapping("/top-gainers")
    public ResponseEntity<List<Stock>> getTopGainers() {
        List<Stock> topGainers = stockService.getTopGainers();
        return new ResponseEntity<>(topGainers, HttpStatus.OK);
    }

    @GetMapping("/top-losers")
    public ResponseEntity<List<Stock>> getTopLosers() {
        List<Stock> topLosers = stockService.getTopLosers();
        return new ResponseEntity<>(topLosers, HttpStatus.OK);
    }
}
