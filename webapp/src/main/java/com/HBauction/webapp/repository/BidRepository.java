package com.HBauction.webapp.repository;

import com.HBauction.webapp.model.Bid;
import com.HBauction.webapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * JpaRepository for Bid
 */

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByItem(Item item); 
}
