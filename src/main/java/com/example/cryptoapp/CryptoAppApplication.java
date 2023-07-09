package com.example.cryptoapp;

import com.example.cryptoapp.service.h2_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoAppApplication {

    public static void main(String[] args){

        SpringApplication.run(CryptoAppApplication.class, args);
        h2_service.start();
    }
}

