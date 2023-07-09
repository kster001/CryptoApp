package com.example.cryptoapp.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.cryptoapp.model.Wallets;

import java.math.BigDecimal;

@Repository
public interface WalletsRepo extends JpaRepository<Wallets, Long> {
    Wallets findByUsername(String user_name);
}
