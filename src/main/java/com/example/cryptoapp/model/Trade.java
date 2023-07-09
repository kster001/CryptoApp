package com.example.cryptoapp.model;

public class Trade {

    private String user_name;

    private String transaction_type;

    private Double size;

    private long crypto_pricing_id;

    public Trade(String user_name, String transaction_type, Double size, long crypto_pricing_id) {
        this.user_name = user_name;
        this.transaction_type = transaction_type;
        this.size = size;
        this.crypto_pricing_id = crypto_pricing_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public long getCrypto_pricing_id() {
        return crypto_pricing_id;
    }

    public void setCrypto_pricing_id(long crypto_pricing_id) {
        this.crypto_pricing_id = crypto_pricing_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
