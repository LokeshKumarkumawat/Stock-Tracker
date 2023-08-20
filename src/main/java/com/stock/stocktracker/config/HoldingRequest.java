package com.stock.stocktracker.config;

public class HoldingRequest {

    private int quantity;
    private double averagePrice;

    // Constructors, getters, setters...

    public HoldingRequest() {
    }

    public HoldingRequest(int quantity, double averagePrice) {
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }
}
