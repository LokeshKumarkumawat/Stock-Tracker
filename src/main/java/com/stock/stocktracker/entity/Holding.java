package com.stock.stocktracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private int quantity;
    private double averagePrice;

    public Holding(User user, Stock stock, int quantity, double averagePrice) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }
}