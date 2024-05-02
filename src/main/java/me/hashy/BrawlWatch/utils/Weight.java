package me.hashy.BrawlWatch.utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

import brawljars.api.intern.players.PlayerApi;
import brawljars.api.intern.players.info.Brawler;
import brawljars.api.intern.players.info.PlayerRequest;
import brawljars.api.intern.players.info.PlayerResponse;
import me.hashy.BrawlWatch.Main;

public class Weight {

    public static double getWeight(String tag) {
        PlayerApi api = Main.getBrawlJars().getApi(PlayerApi.class);
        PlayerResponse response = null;
        try {
            response = api.findById(PlayerRequest.builder(tag)
                    .playerTag(tag)
                    .storeRawResponse(true)
                    .build()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    
        double weight = 0;
        int totalTrophies = 0;
    
        for (Brawler b : response.getBrawlers()) {
            totalTrophies += b.getHighestTrophies();
        }
    
        double averageTrophies = totalTrophies / (double) response.getBrawlers().size();
        double highTrophyThreshold = averageTrophies * 1.5;
        double lowTrophyThreshold = averageTrophies * 0.5;
        double maxTrophies = response.getBrawlers().stream()
                                .mapToInt(Brawler::getHighestTrophies)
                                .max().orElse(1);
    
        int highTrophyBrawlers = 0;
        int lowTrophyBrawlers = 0;
        double eulersNumber = Math.E;
    
        for (Brawler b : response.getBrawlers()) {
            int trophies = b.getHighestTrophies();
            double normalizedTrophies = (double) trophies / maxTrophies;
            double dynamicFactor = 1.0;
    
            if (trophies > highTrophyThreshold) {
                highTrophyBrawlers++;
            } else if (trophies < lowTrophyThreshold) {
                lowTrophyBrawlers++;
            }
    
            if (trophies > 700) {
                dynamicFactor = Math.pow(eulersNumber, (trophies - 700) / 50.0);
            }
    
            weight += (1 / (1 + Math.exp(-0.005 * (normalizedTrophies - highTrophyThreshold)))) * dynamicFactor;
        }
    
        double brawlerFactor = 2 / (1 + Math.exp(0.10 * highTrophyBrawlers));
        double lowTrophyPenalty = 1 / (1 + Math.exp(-0.05 * lowTrophyBrawlers));
        double trophySpreadFactor = calculateTrophySpreadFactor(response.getBrawlers(), averageTrophies);
    
        double finalScore = weight * brawlerFactor * lowTrophyPenalty * trophySpreadFactor;
    
        return Math.min(1000, finalScore);
    }
    
    private static double calculateTrophySpreadFactor(List<Brawler> brawlers, double averageTrophies) {
        double variance = 0;
        for (Brawler b : brawlers) {
            double trophyDifference = b.getHighestTrophies() - averageTrophies;
            variance += trophyDifference * trophyDifference;
        }
        double standardDeviation = Math.sqrt(variance / brawlers.size());
        return 1 + (standardDeviation / averageTrophies);
    }


    /* Find a way to get mythic gears and hypercharges, likely impossible without reverse engineering the app
    private static int getCoins(List<Brawler> list) {
        int i = 0;
        for (Brawler b : list) {
            i += getTotalCoins(b.getRank()) + b.getGadgets().size() * 1000
                    + b.getGears().size() * 1000 + b.getStarPowers().size() * 2000;
        }
        return i;
    }
    

    private static int getPowerpoints(List<Brawler> list) {
        int i = 0;
        for (Brawler b : list) {
            i += getTotalPowerPoints(b.getRank());
        }
        return i;
    }
    

    private static int getTotalPowerPoints(int level) {
        final int[] TOTAL_POWER_POINTS = { 0, 0, 20, 50, 100, 180, 310, 520, 860, 1410, 2300, 3740 };
        if (level >= 1 && level < TOTAL_POWER_POINTS.length) {
            return TOTAL_POWER_POINTS[level];
        } else {
            return -1;
        }
    }

    private static int getTotalCoins(int level) {
        final int[] TOTAL_COINS = { 0, 0, 20, 55, 130, 270, 560, 1040, 1840, 3090, 4965, 7765 };
        if (level >= 1 && level < TOTAL_COINS.length) {
            return TOTAL_COINS[level];
        } else {
            return -1;
        }
    }
    */
}
