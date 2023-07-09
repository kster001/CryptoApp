package com.example.cryptoapp.respository;

import com.example.cryptoapp.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepo extends JpaRepository<Trade, Long> {
    Trade findByCryptoPricingId(long crypto_pricing_id);
}
