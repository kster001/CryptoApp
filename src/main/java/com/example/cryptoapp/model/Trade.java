package com.example.cryptoapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TRADE")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "size")
    private Double size;

    @Column(name = "crypto_pricing_id")
    private long cryptoPricingId;

    public Trade(String username, Double size, long cryptoPricingId) {
        this.username = username;
        this.size = size;
        this.cryptoPricingId = cryptoPricingId;
    }

    public Trade() {

    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCryptoPricingId() {
        return cryptoPricingId;
    }

    public void setCryptoPricingId(long cryptoPricingId) {
        this.cryptoPricingId = cryptoPricingId;
    }
}
