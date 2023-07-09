package com.example.cryptoapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "CRYPTO_PRICING")
public class CryptoPricing {

    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "bid_price")
    private BigDecimal bidPrice;

    @Column(name = "bid_size")
    private Double bidSize;

    @Column(name = "ask_price")
    private BigDecimal askPrice;

    @Column(name = "ask_size")
    private Double askSize;

    @Column(name = "source")
    private String source;

    @Column(name = "date/time")
    private String datetime = dateFormat.format(new Date());

    public CryptoPricing(String symbol, BigDecimal bidPrice, Double bidSize, BigDecimal askPrice, Double askSize,
    String source) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.source = source;
    }

    public CryptoPricing() {

    }


    public Double getAskSize() {
        return askSize;
    }

    public void setAskSize(Double askSize) {
        this.askSize = askSize;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(BigDecimal askPrice) {
        this.askPrice = askPrice;
    }

    public Double getBidSize() {
        return bidSize;
    }

    public void setBidSize(Double bidSize) {
        this.bidSize = bidSize;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDatetime() {
        return new Date(datetime);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
