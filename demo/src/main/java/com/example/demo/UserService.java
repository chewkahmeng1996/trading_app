package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void updateBalance(User user, String crypto, Double amount) {
        switch (crypto.toUpperCase()) {
            case "BTC": user.setBtcBalance(user.getBtcBalance() + amount); break;
            case "ETH": user.setEthBalance(user.getEthBalance() + amount); break;
            case "USDT": user.setUsdtBalance(user.getUsdtBalance() + amount); break;
        }
        userRepository.save(user);
    }
}