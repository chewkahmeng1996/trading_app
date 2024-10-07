package com.example.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BestPriceService {

    @Autowired
    private BestPriceRepository bestPriceRepository;

    public Optional<BestPrice> getLatestBestPrice(String cryptoPair) {
        Pageable pageable = PageRequest.of(0, 1); // Get the first page with 1 item
        List<BestPrice> latestPrices = bestPriceRepository.findLatestByCryptoPair(cryptoPair, pageable);
        return latestPrices.isEmpty() ? Optional.empty() : Optional.of(latestPrices.get(0));
    }
}
