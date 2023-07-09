package com.example.cryptoapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION_HISTORY")
public class TransactionHistory {

    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "size")
    private Double size;

    @Column(name = "totalCost")
    private BigDecimal totalCost;

    @Column(name = "crypto_pricing_id")
    private long cryptoPricingId;

    @Column(name = "date/time")
    private String datetime = dateFormat.format(new Date());


    public TransactionHistory(String username, String transactionType, Double size, BigDecimal totalCost,
                              long cryptoPricingId) {
        this.username = username;
        this.transactionType = transactionType;
        this.size = size;
        this.totalCost = totalCost;
        this.cryptoPricingId = cryptoPricingId;
    }

    public TransactionHistory() {

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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public long getCrypto_pricing_id() {
        return cryptoPricingId;
    }

    public void setCrypto_pricing_id(long cryptoPricingId) {
        this.cryptoPricingId = cryptoPricingId;
    }

    public Date getDatetime() {
        return new Date(datetime);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
