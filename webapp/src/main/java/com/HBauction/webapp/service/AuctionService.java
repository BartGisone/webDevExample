package com.HBauction.webapp.service;

import com.HBauction.webapp.model.Bid;
import com.HBauction.webapp.model.Item;
import com.HBauction.webapp.model.User;
import com.HBauction.webapp.repository.BidRepository;
import com.HBauction.webapp.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;


@Service
public class AuctionService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemRepository itemRepository;
    
    /*
     * sets the highest bidder, and currentPrice.
     * saves the item and saves the bid to respected repos.
     * if bid isn't valid, returns null.
     */
    public Bid placeBid(User user, Item item, double amount) {
        if (amount > item.getCurrentPrice()) {
            item.setCurrentPrice(amount);
            item.setHighestBidder(user); 
            itemRepository.save(item); 

            Bid bid = new Bid();
            bid.setBidder(user);
            bid.setItem(item);
            bid.setAmount(amount);
            return bidRepository.save(bid);
        }
        return null; 
    }
    /*
     * if Dutch auction, sets the highestBidder, sets the curretnt price,
     * and saves item to repo.
     */
    public boolean buyNow(User user, Item item) {
        if ("Dutch".equalsIgnoreCase(item.getAuctionType())) {
            item.setHighestBidder(user); 
            item.setCurrentPrice(item.getStartingPrice()); 
            itemRepository.save(item); 
            return true;
        }
        return false;
    }
    
    /*
     * gets the highest bidder for a selected item, if no highest bidder,
     * returns false.
     */
    public User getHighestBidder(Item item) {
        Optional<Bid> highestBid = bidRepository.findByItem(item)
                .stream()
                .max(Comparator.comparingDouble(Bid::getAmount));

        if (highestBid.isPresent()) {
            User highestBidder = highestBid.get().getBidder();
            item.setHighestBidder(highestBidder); 
            itemRepository.save(item); 
            return highestBidder;
        }
        return null; 
    }


}
