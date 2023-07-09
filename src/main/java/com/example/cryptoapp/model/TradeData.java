package com.example.cryptoapp.model;

public class TradeData {
    private String username;

    private Double size;

    private long cryptoPricingId;

    private String transactionType;

    public TradeData(String username, Double size, long cryptoPricingId, String transactionType) {
        this.username = username;
        this.size = size;
        this.cryptoPricingId = cryptoPricingId;
        this.transactionType = transactionType;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public long getCryptoPricingId() {
        return cryptoPricingId;
    }

    public void setCryptoPricingId(long cryptoPricingId) {
        this.cryptoPricingId = cryptoPricingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
