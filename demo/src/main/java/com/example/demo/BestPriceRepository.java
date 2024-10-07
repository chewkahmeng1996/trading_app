package com.example.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface BestPriceRepository extends JpaRepository<BestPrice, Long> {

    // Custom query to find the best price by crypto pair
    List<BestPrice> findByCryptoPair(String cryptoPair);

    @Query("SELECT bp FROM BestPrice bp WHERE bp.cryptoPair = :cryptoPair ORDER BY bp.timestamp DESC")
    List<BestPrice> findLatestByCryptoPair(@Param("cryptoPair") String cryptoPair, Pageable pageable);
    
}
