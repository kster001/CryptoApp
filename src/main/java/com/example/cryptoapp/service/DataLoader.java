package com.example.cryptoapp.service;

import com.example.cryptoapp.model.Wallets;
import com.example.cryptoapp.respository.WalletsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    private WalletsRepo walletsRepo;

    @Autowired
    public DataLoader(WalletsRepo walletsRepo) {
        this.walletsRepo = walletsRepo;
    }

    public void run(ApplicationArguments args) {
        walletsRepo.save(new Wallets("user1", BigDecimal.valueOf(50000)));
    }
}
