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
    private String user_name;

    @Column(name = "transaction_type")
    private BigDecimal transactionType;

    @Column(name = "size")
    private Double size;

    @Column(name = "totalCost")
    private BigDecimal totalCost;

    @Column(name = "ask_size")
    private long crypto_pricing_id;

    @Column(name = "date/time")
    private String datetime = dateFormat.format(new Date());


    public TransactionHistory(long id, String user_name, BigDecimal transactionType, Double size, BigDecimal totalCost, long crypto_pricing_id) {
        this.id = id;
        this.user_name = user_name;
        this.transactionType = transactionType;
        this.size = size;
        this.totalCost = totalCost;
        this.crypto_pricing_id = crypto_pricing_id;
    }

    public TransactionHistory() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public BigDecimal getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BigDecimal transactionType) {
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
        return crypto_pricing_id;
    }

    public void setCrypto_pricing_id(long crypto_pricing_id) {
        this.crypto_pricing_id = crypto_pricing_id;
    }

    public Date getDatetime() {
        return new Date(datetime);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
