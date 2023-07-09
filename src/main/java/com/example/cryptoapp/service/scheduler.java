package com.example.cryptoapp.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class scheduler {
    HttpClient client = HttpClient.newHttpClient();

    @Scheduled(cron="*/10 * * * * *")
    public void reportCurrentTime() throws IOException, InterruptedException {
        System.out.println("Retrieving pricing from sources");
        try {
            var request = HttpRequest.newBuilder(
                    URI.create("http://localhost:8080/api/cryptoPricings/getPricingFromSources"))
                    .header("accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            // use the client to send the request
            var responseFuture = client.sendAsync(request,  HttpResponse.BodyHandlers.ofString());

            var response = responseFuture.get();

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Retrieved pricing from sources successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
