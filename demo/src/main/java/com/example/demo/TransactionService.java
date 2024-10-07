package com.example.demo;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public void performTransaction(String username, String crypto, String action, Double amount, Double price) {
        
        if (!isValidTradingPair(crypto)) {
            throw new IllegalArgumentException("Unsupported trading pair: " + crypto);
        }
        
        User user = userService.getUserByUsername(username);

        // Adjust wallet balance based on action
        if (action.equalsIgnoreCase("buy")) {
            userService.updateBalance(user, crypto, amount);
        } else if (action.equalsIgnoreCase("sell")) {
            userService.updateBalance(user, crypto, -amount);
        }

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setPrice(price);
        transaction.setCryptoPair(crypto + "USDT"); // Set the crypto pair here
        transaction.setTransactionType(action);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    private boolean isValidTradingPair(String crypto) {
        return "ETH".equalsIgnoreCase(crypto) || "BTC".equalsIgnoreCase(crypto);
    }

    public List<Transaction> getTransactions(String username) {
        User user = userService.getUserByUsername(username);
        return transactionRepository.findByUser(user);
    }
}
