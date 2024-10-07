package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/best-prices")
public class BestPriceController {

    @Autowired
    private BestPriceService bestPriceService;

    @GetMapping("/{cryptoPair}/latest")
    public Optional<BestPrice> getLatestBestPrice(@PathVariable String cryptoPair) {
        return bestPriceService.getLatestBestPrice(cryptoPair);
    }
}
