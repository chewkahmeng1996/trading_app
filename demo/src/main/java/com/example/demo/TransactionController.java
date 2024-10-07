package com.example.demo;

import java.util.List;
import org.springframework.web.bind.annotation.*; // Import RestController and RequestMapping
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeTransaction(
            @RequestParam String username,
            @RequestParam String crypto,
            @RequestParam String action,
            @RequestParam Double amount,
            @RequestParam Double price) {
        try {
            // Call the service to perform the transaction
            transactionService.performTransaction(username, crypto, action, amount, price);
            return ResponseEntity.ok("Transaction completed successfully");
        } catch (Exception e) {
            // Log the error and return a response indicating failure
            return ResponseEntity.status(500).body("Transaction failed: " + e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public List<Transaction> getUserTransactions(@PathVariable String username) {
        return transactionService.getTransactions(username);
    }
}
