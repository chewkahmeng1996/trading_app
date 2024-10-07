package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/latest-price/{cryptoPair}")
    public Optional<BestPrice> getLatestPrice(@PathVariable String cryptoPair) {
        return tradeService.getLatestBestPrice(cryptoPair);
    }

    @PostMapping("/execute")
    public Trade executeTrade(@RequestParam String username, @RequestParam String cryptoPair, @RequestParam BigDecimal amount, @RequestParam boolean isBuyTrade) {
        return tradeService.executeTrade(username, cryptoPair, amount, isBuyTrade);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Trade>> getUserTrades(@PathVariable String username) {
        List<Trade> trades = tradeService.getUserTradesByUsername(username);
        return ResponseEntity.ok(trades);
    }
}
