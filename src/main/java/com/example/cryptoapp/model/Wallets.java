package com.example.cryptoapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "USERS_WALLET")
public class Wallets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "balance")
    private BigDecimal balance;


    public Wallets(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }

    public Wallets() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
