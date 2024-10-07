package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    // Additional query methods can be defined here
    List<Trade> findByUser_Username(String username);
}
