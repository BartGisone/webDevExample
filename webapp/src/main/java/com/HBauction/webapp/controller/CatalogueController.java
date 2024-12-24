package com.HBauction.webapp.controller;

import com.HBauction.webapp.model.Item;
import com.HBauction.webapp.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogueController {

    @Autowired
    private CatalogueService catalogueService;
    
    /*
     *  Search page
     */
    @GetMapping("/search")
    public String showSearchPage() {
        return "search"; 
    }
    
    /*
     * Results page
     * returns the results of a search for 'keyword'
     */
    @GetMapping("/results")
    public String showSearchResults(@RequestParam("keyword") String keyword, Model model) {
    
        keyword = keyword != null ? keyword.trim() : "";
        if (keyword.isEmpty()) {
            model.addAttribute("items", null); 
            return "results";
        }

        List<Item> items = catalogueService.searchItemsByKeyword(keyword);
        


        model.addAttribute("items", items);
        return "results"; 
    }
}
