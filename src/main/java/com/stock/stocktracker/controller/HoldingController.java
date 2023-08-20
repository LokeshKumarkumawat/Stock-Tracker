package com.stock.stocktracker.controller;

import com.stock.stocktracker.config.HoldingRequest;
import com.stock.stocktracker.entity.Holding;
import com.stock.stocktracker.entity.User;
import com.stock.stocktracker.repository.HoldingRepository;
import com.stock.stocktracker.service.HoldingService;
import com.stock.stocktracker.service.StockService;
import com.stock.stocktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/holdings")
public class HoldingController {

    private final HoldingService holdingService;
    private final UserService userService;
    private final StockService stockService;
    private final HoldingRepository holdingRepository;

    @Autowired
    public HoldingController(HoldingService holdingService, UserService userService, StockService stockService, HoldingRepository holdingRepository) {
        this.holdingService = holdingService;
        this.userService = userService;
        this.stockService = stockService;
        this.holdingRepository = holdingRepository;
    }

    @PostMapping("/{userId}/{stockSymbol}")
    public ResponseEntity<Holding> addHolding(
            @PathVariable Long userId,
            @PathVariable String stockSymbol,
            @RequestBody HoldingRequest holdingRequest) {

        Holding holding = holdingService.addHolding(userId, stockSymbol, holdingRequest);
        return new ResponseEntity<>(holding, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Holding>> getUserHoldings(@PathVariable Long userId) {
        List<Holding> holdings = holdingService.getHoldingsByUserId(userId);
        return new ResponseEntity<>(holdings, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{stockSymbol}")
    public ResponseEntity<Holding> getUserStockHolding(
            @PathVariable Long userId,
            @PathVariable String stockSymbol
    ) {
        Holding holding = holdingService.getHoldingByUserIdAndStockSymbol(userId, stockSymbol);
        return new ResponseEntity<>(holding, HttpStatus.OK);
    }



//    @PutMapping("/{userId}/{stockSymbol}")
//    public ResponseEntity<Holding> updateHoldingQuantity(
//            @PathVariable Long userId,
//            @PathVariable String stockSymbol,
//            @RequestParam int newQuantity
//    ) {
//        Holding updatedHolding = holdingService.updateHoldingQuantity(userId, stockSymbol, newQuantity);
//        return new ResponseEntity<>(updatedHolding, HttpStatus.OK);
//    }
//
    ////////


    @PutMapping("/{userId}/{stockSymbol}")
    public Holding updateHoldingQuantity(Long userId, String stockSymbol, int newQuantity) {
        User user = userService.getUserById(userId);
        Holding holding = getHoldingByUserIdAndStockSymbol(userId, stockSymbol);

        int currentQuantity = holding.getQuantity();
        double averagePrice = holding.getAveragePrice();

        holding.setQuantity(newQuantity);

        // Calculate and update the average price if the quantity changes
        if (newQuantity != currentQuantity) {
            double updatedAveragePrice = calculateUpdatedAveragePrice(
                    currentQuantity, averagePrice, newQuantity
            );
            holding.setAveragePrice(updatedAveragePrice);
        }

        return holdingRepository.save(holding);
    }

    private double calculateUpdatedAveragePrice(
            int currentQuantity, double currentAveragePrice, int newQuantity) {
        // Calculate the updated average price based on current quantity, average price, and new quantity
        // Adjust the calculation based on your specific requirements
        return ((currentAveragePrice * currentQuantity) + (0.0 /* new purchase price */ * newQuantity))
                / (currentQuantity + newQuantity);
    }

    public Holding getHoldingByUserIdAndStockSymbol(Long userId, String stockSymbol) {
        User user = userService.getUserById(userId);
        return holdingRepository.findByUserAndStockSymbol(user, stockSymbol);
    }

    @DeleteMapping("/{userId}/{stockSymbol}")
    public ResponseEntity<String> deleteHolding(
            @PathVariable Long userId,
            @PathVariable String stockSymbol
    ) {
        holdingService.deleteHolding(userId, stockSymbol);
        return new ResponseEntity<>("Holding deleted successfully", HttpStatus.NO_CONTENT);
    }
}


