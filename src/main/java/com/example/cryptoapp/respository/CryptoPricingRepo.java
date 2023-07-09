package com.example.cryptoapp.respository;

import com.example.cryptoapp.model.CryptoPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPricingRepo extends JpaRepository<CryptoPricing, Long>{
}
