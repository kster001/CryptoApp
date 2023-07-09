package com.example.cryptoapp.service;

import com.example.cryptoapp.model.CryptoPricing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingRetriever {
    HttpClient client = HttpClient.newHttpClient();

    private final List<CryptoPricing> cryptoPricingList;

    public PricingRetriever() throws JSONException, IOException, InterruptedException {
        this.cryptoPricingList = new ArrayList<>();
        this.getPricingFromBinance();
        this.getPricingFromHuobi();
    }

    private void getPricingFromBinance() throws IOException, InterruptedException, JSONException {
        var request = HttpRequest.newBuilder(
                URI.create("https://api.binance.com/api/v3/ticker/bookTicker?symbols=[%22BTCUSDT%22,%22ETHUSDT%22]"))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray data = new JSONArray(response.body());
        try {

            for (int i = 0; i < data.length(); i++) {
                JSONObject crypto = data.getJSONObject(i);
                BigDecimal bidPrice = new BigDecimal(crypto.getDouble("bidPrice"), MathContext.DECIMAL64);
                BigDecimal askPrice = new BigDecimal(crypto.getDouble("askPrice"), MathContext.DECIMAL64);
                this.cryptoPricingList.add(new CryptoPricing(crypto.getString("symbol"), bidPrice,
                        crypto.getDouble("bidQty"), askPrice, crypto.getDouble("askQty"), "Binance"));

            }
        } catch(Exception e){
            System.out.println(e.getMessage());

        }
    }

    private void getPricingFromHuobi() throws IOException, InterruptedException, JSONException {
        var request = HttpRequest.newBuilder(
                URI.create("https://api.huobi.pro/market/tickers"))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject rawData = new JSONObject(response.body());
        JSONArray data = rawData.getJSONArray("data");
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject crypto = data.getJSONObject(i);
                String symbol = crypto.getString("symbol").toUpperCase();
                BigDecimal bidPrice = new BigDecimal(crypto.getDouble("bid"), MathContext.DECIMAL64);
                BigDecimal askPrice = new BigDecimal(crypto.getDouble("ask"), MathContext.DECIMAL64);

                if(symbol.equals("ETHUSDT") || symbol.equals("BTCUSDT")) {
                    cryptoPricingList.add(new CryptoPricing(symbol, bidPrice, crypto.getDouble("bidSize"),
                            askPrice, crypto.getDouble("askSize"), "Huobi"));
                }


            }
        } catch(Exception e){
            System.out.println(e.getMessage());

        }
    }

    public List<CryptoPricing> getCryptoPricingList() {
        return cryptoPricingList;
    }

}
