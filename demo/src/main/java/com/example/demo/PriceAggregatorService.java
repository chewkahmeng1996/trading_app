package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.LocalDateTime;

@Service
public class PriceAggregatorService {

    private final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
    private final String HUOBI_URL = "https://api.huobi.pro/market/tickers";

    @Autowired
    private BestPriceRepository bestPriceRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void fetchBestPrices() {
        try {
            // Fetch prices from Binance
            JSONArray binanceData = new JSONArray(restTemplate.getForObject(BINANCE_URL, String.class));
            JSONObject binanceBTC = findCryptoPair(binanceData, "BTCUSDT");
            JSONObject binanceETH = findCryptoPair(binanceData, "ETHUSDT");

            // Fetch prices from Huobi
            JSONObject huobiResponse = new JSONObject(restTemplate.getForObject(HUOBI_URL, String.class));
            JSONArray huobiData = huobiResponse.getJSONArray("data");
            JSONObject huobiBTC = findCryptoPair(huobiData, "btcusdt");
            JSONObject huobiETH = findCryptoPair(huobiData, "ethusdt");

            // Get best prices for BTC
            BestPrice bestBTCPrice = getBestPrice("BTCUSDT", binanceBTC, huobiBTC);
            bestPriceRepository.save(bestBTCPrice);

            // Get best prices for ETH
            BestPrice bestETHPrice = getBestPrice("ETHUSDT", binanceETH, huobiETH);
            bestPriceRepository.save(bestETHPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject findCryptoPair(JSONArray dataArray, String cryptoPair) {
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            String symbol = obj.has("symbol") ? obj.getString("symbol") : obj.getString("symbol");
            if (symbol.equalsIgnoreCase(cryptoPair)) {
                return obj;
            }
        }
        return null;
    }

    private BestPrice getBestPrice(String cryptoPair, JSONObject binanceData, JSONObject huobiData) {
        Double binanceBidPrice = binanceData != null ? binanceData.getDouble("bidPrice") : Double.MIN_VALUE;
        Double binanceAskPrice = binanceData != null ? binanceData.getDouble("askPrice") : Double.MAX_VALUE;

        Double huobiBidPrice = huobiData != null ? huobiData.getDouble("bid") : Double.MIN_VALUE;
        Double huobiAskPrice = huobiData != null ? huobiData.getDouble("ask") : Double.MAX_VALUE;

        // Determine best Bid (Sell) price
        Double bestBidPrice = binanceBidPrice > huobiBidPrice ? binanceBidPrice : huobiBidPrice;
        String exchangeForBid = binanceBidPrice > huobiBidPrice ? "Binance" : "Huobi";

        // Determine best Ask (Buy) price
        Double bestAskPrice = binanceAskPrice < huobiAskPrice ? binanceAskPrice : huobiAskPrice;
        String exchangeForAsk = binanceAskPrice < huobiAskPrice ? "Binance" : "Huobi";

        BestPrice bestPrice = new BestPrice();
        bestPrice.setCryptoPair(cryptoPair);
        bestPrice.setBestBidPrice(bestBidPrice);
        bestPrice.setBestAskPrice(bestAskPrice);
        bestPrice.setExchangeForBid(exchangeForBid);
        bestPrice.setExchangeForAsk(exchangeForAsk);
        bestPrice.setTimestamp(LocalDateTime.now());

        return bestPrice;
    }
}
