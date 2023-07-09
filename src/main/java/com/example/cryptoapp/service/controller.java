package com.example.cryptoapp.service;

import com.example.cryptoapp.model.*;
import com.example.cryptoapp.respository.CryptoPricingRepo;
import com.example.cryptoapp.respository.TradeRepo;
import com.example.cryptoapp.respository.TransHistoryRepo;
import com.example.cryptoapp.respository.WalletsRepo;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class controller {

    @Autowired
    CryptoPricingRepo cryptoPricingRepo;

    @Autowired
    WalletsRepo walletsRepo;

    @Autowired
    TransHistoryRepo transHistoryRepo;

    @Autowired
    TradeRepo tradeRepo;

    @PostMapping("/cryptoPricings/getPricingFromSources")
    public ResponseEntity<List<CryptoPricing>> getPricingFromSources() {
        try{
            PricingRetriever pricingRetriever = new PricingRetriever();
            List<CryptoPricing> _cp = cryptoPricingRepo.saveAll(pricingRetriever.getCryptoPricingList());
            return new ResponseEntity<>(_cp, HttpStatus.CREATED);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/cryptoPricings")
    public ResponseEntity<List<CryptoPricing>> getLatestPricing() {
        try {
            List<CryptoPricing> cryptoPricingList = cryptoPricingRepo.findAll();

            if (cryptoPricingList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Collections.sort(cryptoPricingList, (cp1, cp2) -> cp2.getDatetime().
                    compareTo(cp1.getDatetime()));
            return new ResponseEntity<>(cryptoPricingList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/wallets/{username}")
    public ResponseEntity<Wallets> getWalletByUsername(@PathVariable("username") String username) {
        Wallets walletsData = walletsRepo.findByUsername(username);

        return new ResponseEntity<>(walletsData, HttpStatus.OK);

    }

    @GetMapping("/transactionHistory/{username}")
    public ResponseEntity<List<TransactionHistory>> getTransactionHistory(@PathVariable("username") String username) {
        try {
            List<TransactionHistory> transactionHistoryList = transHistoryRepo.findAllByUsername(username);

            if (transactionHistoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Collections.sort(transactionHistoryList, (cp1, cp2) -> cp2.getDatetime().
                    compareTo(cp1.getDatetime()));
            return new ResponseEntity<>(transactionHistoryList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trade")
    public ResponseEntity<String> trade(@RequestBody TradeData tradeData) {
        BigDecimal newBalance = BigDecimal.valueOf(0);
        String transactionType = tradeData.getTransactionType();
        long cryptoPricingId = tradeData.getCryptoPricingId();
        Optional<CryptoPricing> cryptoPricingData = cryptoPricingRepo.findById(cryptoPricingId);
        Wallets wallet = walletsRepo.findByUsername(tradeData.getUsername());
        if (cryptoPricingData.isPresent()) {
            CryptoPricing cryptoPricing = cryptoPricingData.get();
            double size = tradeData.getSize();
            BigDecimal currentBalance = wallet.getBalance();
            BigDecimal amount = null;

            if (transactionType.equals("buy")) {
                amount = cryptoPricing.getAskPrice().multiply(BigDecimal.valueOf(size));

                if (amount.compareTo(currentBalance) > 0) {
                    return new ResponseEntity<>("Total amount exceeded wallet balance", HttpStatus.BAD_REQUEST);
                }

                this.setTrade(transactionType, tradeData, size);
                newBalance = currentBalance.subtract(amount);

            } else if (transactionType.equals("sell")) {
                amount = cryptoPricing.getBidPrice().multiply(BigDecimal.valueOf(size));
                String result = this.setTrade(transactionType, tradeData, size);
                if(result != null) {
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
                newBalance = currentBalance.add(amount);

            }

            wallet.setBalance(newBalance);
            walletsRepo.save(wallet);

            transHistoryRepo.save(new TransactionHistory(tradeData.getUsername(), transactionType, size, amount,
            cryptoPricingId));
            return new ResponseEntity<>(null, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    public String setTrade(String transactionType, TradeData tradeData, double size) {
        Optional<Trade> tradeResult = Optional.ofNullable(tradeRepo.findByCryptoPricingId(tradeData.getCryptoPricingId()));
        if(tradeResult.isPresent()) {
            Trade existTrade = tradeResult.get();
            double existSize = existTrade.getSize();
            if(transactionType.equals("buy")) {
                existTrade.setSize(existSize + size);
            } else {
                double remainingSize = existSize - size;

                if(remainingSize < 0) {
                    return "Provided Size is more than exist Size";
                }

                if(remainingSize == 0) {
                    tradeRepo.delete(existTrade);
                    return null;
                }

                existTrade.setSize(remainingSize);
            }


            tradeRepo.save(existTrade);
        } else {
            if(transactionType.equals("sell")) {
                return "Share not found";
            }
            Trade trade = new Trade(tradeData.getUsername(), tradeData.getSize(), tradeData.getCryptoPricingId());
            tradeRepo.save(trade);
        }

        return null;
    }
}
