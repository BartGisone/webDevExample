package com.HBauction.webapp.repository;

import com.HBauction.webapp.model.Item; 
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * JpaRepository for Item
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainingIgnoreCase(String keyword); 
}
