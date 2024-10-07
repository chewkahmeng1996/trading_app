package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BEST_PRICE") // Ensure this matches your database table name
public class BestPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cryptoPair;
    private Double bestBidPrice;
    private Double bestAskPrice;
    private String exchangeForBid;
    private String exchangeForAsk;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public BestPrice() {}

    public BestPrice(String cryptoPair, Double bestBidPrice, Double bestAskPrice, String exchangeForBid, String exchangeForAsk, LocalDateTime timestamp) {
        this.cryptoPair = cryptoPair;
        this.bestBidPrice = bestBidPrice;
        this.bestAskPrice = bestAskPrice;
        this.exchangeForBid = exchangeForBid;
        this.exchangeForAsk = exchangeForAsk;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCryptoPair() {
        return cryptoPair;
    }

    public void setCryptoPair(String cryptoPair) {
        this.cryptoPair = cryptoPair;
    }

    public Double getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(Double bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public Double getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(Double bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public String getExchangeForBid() {
        return exchangeForBid;
    }

    public void setExchangeForBid(String exchangeForBid) {
        this.exchangeForBid = exchangeForBid;
    }

    public String getExchangeForAsk() {
        return exchangeForAsk;
    }

    public void setExchangeForAsk(String exchangeForAsk) {
        this.exchangeForAsk = exchangeForAsk;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
