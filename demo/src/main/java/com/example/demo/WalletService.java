package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public BigDecimal getWalletBalance(String username) {
        Optional<Wallet> walletOpt = walletRepository.findByUser_Username(username);
        return walletOpt.map(Wallet::getBalance).orElse(BigDecimal.ZERO); // Return 0 if wallet not found
    }
}
