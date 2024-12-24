package com.HBauction.webapp.service;

import java.util.Date; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.HBauction.webapp.model.Item;

/*
 * A service for Auctions which handles expired services.
 * it is ran every minute(fixedRate = 60,000), and updates the
 * item when it expires.
 */
@Service
public class AuctionExpirationService {

    @Autowired
    private CatalogueService catalogueService;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkAndHandleExpiredAuctions() {
        List<Item> allItems = catalogueService.getAllItems(); // Fetch all items
        Date now = new Date();

        for (Item item : allItems) {
            if ("Forward".equalsIgnoreCase(item.getAuctionType()) && item.getAuctionEndTime().before(now)) {
               
                // Update item as expired
                item.setAuctionType("Expired");
                catalogueService.saveItem(item);
            }
        }
    }
}
