package com.example.demo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TradeService {

    @Autowired
    private BestPriceRepository bestPriceRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<BestPrice> getLatestBestPrice(String cryptoPair) {
        Pageable pageable = PageRequest.of(0, 1); // Get the first page with 1 item
        List<BestPrice> latestPrices = bestPriceRepository.findLatestByCryptoPair(cryptoPair, pageable);
        return latestPrices.isEmpty() ? Optional.empty() : Optional.of(latestPrices.get(0));
    }

    public Trade executeTrade(String username, String cryptoPair, BigDecimal amount, boolean isBuyTrade) {
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<BestPrice> latestPriceOpt = getLatestBestPrice(cryptoPair);
        if (latestPriceOpt.isPresent()) {
            Trade trade = new Trade();
            trade.setUser(user); // Set the user before saving the trade
            trade.setCryptoPair(cryptoPair);
            if (isBuyTrade) {
                trade.setTradePrice(latestPriceOpt.get().getBestAskPrice()); // Use ask price for buying
            } else {
                trade.setTradePrice(latestPriceOpt.get().getBestBidPrice()); // Use bid price for selling
            }
            trade.setTradeAmount(amount);
            return tradeRepository.save(trade);
        } else {
            System.out.println("No price found for crypto pair: " + cryptoPair); // Debugging line
            throw new IllegalArgumentException("No price available for the given crypto pair.");
        }
    }

    public List<Trade> getUserTradesByUsername(String username) {
        // Check if the user exists based on the username
        userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Now retrieve trades by username
        return tradeRepository.findByUser_Username(username);
    }
}
