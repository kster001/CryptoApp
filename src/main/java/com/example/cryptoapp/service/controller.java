package com.example.cryptoapp.service;

import com.example.cryptoapp.model.CryptoPricing;
import com.example.cryptoapp.model.Trade;
import com.example.cryptoapp.model.TransactionHistory;
import com.example.cryptoapp.model.Wallets;
import com.example.cryptoapp.respository.CryptoPricingRepo;
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
    public ResponseEntity<Wallets> getWalletByUsername(@PathVariable("username") String user_name) {
        Wallets walletsData = walletsRepo.findByUsername(user_name);

        return new ResponseEntity<>(walletsData, HttpStatus.OK);

    }

    @GetMapping("/transactionHistory")
    public ResponseEntity<List<TransactionHistory>> getTransactionHistory() {
        try {
            List<TransactionHistory> transactionHistoryList = transHistoryRepo.findAll();

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
    public ResponseEntity<String> trade(@RequestBody Trade trade) {
        BigDecimal newBalance = BigDecimal.valueOf(0);
        Optional<CryptoPricing> cryptoPricingData = cryptoPricingRepo.findById(trade.getCrypto_pricing_id());
        Wallets wallet = walletsRepo.findByUsername(trade.getUser_name());
        if (cryptoPricingData.isPresent()) {
            CryptoPricing cryptoPricing = cryptoPricingData.get();
            double size = trade.getSize();
            BigDecimal currentBalance = wallet.getBalance();
            if (trade.getTransaction_type().equals("buy")) {
                BigDecimal askAmount = cryptoPricing.getAskPrice().multiply(BigDecimal.valueOf(size));

                if (askAmount.compareTo(currentBalance) > 0) {
                    return new ResponseEntity<>("Total amount exceeded wallet balance", HttpStatus.BAD_REQUEST);
                }

                newBalance = currentBalance.subtract(askAmount);

            } else if (trade.getTransaction_type().equals("sell")) {
                BigDecimal bidAmount = cryptoPricing.getBidPrice().multiply(BigDecimal.valueOf(size));
                newBalance = currentBalance.add(bidAmount);
            }

            wallet.setBalance(newBalance);
            walletsRepo.save(wallet);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


    }
}
