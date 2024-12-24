package com.HBauction.webapp.service;

import com.HBauction.webapp.model.Item;
import com.HBauction.webapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogueService {

    @Autowired
    private ItemRepository itemRepository;

    /*
     * Search items by keyword.
     * If Keyword is empty, the returns an empty list.
     * enriches item with remaining time
     */
    public List<Item> searchItemsByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of(); 
        }

        List<Item> items = itemRepository.findByNameContainingIgnoreCase(keyword.trim());
        items.forEach(this::enrichItem);
        return items;
    }

    /*
     * Search items by keyword.
     * If Keyword is empty, the returns an empty list.
     * enriches item with calculated time
     */
    public void enrichItem(Item item) {
        if ("Forward".equalsIgnoreCase(item.getAuctionType())) {
            long timeRemaining = item.getAuctionEndTime().getTime() - new Date().getTime();
            item.setRemainingTime(timeRemaining > 0 ? formatTimeRemaining(timeRemaining) : "Expired");
        } else if ("Dutch".equalsIgnoreCase(item.getAuctionType())) {
            item.setRemainingTime("Now");
        }
    }

    
    /*
     * method used to make time readable for users
     * formats time to days, hours, minutes and seconds
     */
    
    private String formatTimeRemaining(long millis) {
        long seconds = millis / 1000 % 60;
        long minutes = millis / (1000 * 60) % 60;
        long hours = millis / (1000 * 60 * 60) % 24;
        long days = millis / (1000 * 60 * 60 * 24);

        return (days > 0 ? days + "d " : "") +
                (hours > 0 ? hours + "h " : "") +
                (minutes > 0 ? minutes + "m " : "") +
                seconds + "s";
    }

    /*
     * returns an item by itemId
     * if no item is found, returns null
     */
    public Item getItemById(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        return item.orElse(null);
    }
    
    /*
     * returns a list of items
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    /*
     * saves an item to the repository
     */
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    

}
