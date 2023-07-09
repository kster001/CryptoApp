package com.example.cryptoapp.respository;

import com.example.cryptoapp.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransHistoryRepo extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findAllByUsername(String username);
}
