package com.HBauction.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.HBauction.webapp.model.Bid;
import com.HBauction.webapp.model.Item;
import com.HBauction.webapp.model.User;
import com.HBauction.webapp.service.AuctionService;
import com.HBauction.webapp.service.CatalogueService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private CatalogueService catalogueService;

    /*
     * bid Page
     * gets Item by itemId, if item doesn't exist (null), returns error.
     * if item is expired, then updates highestBidder and item to model.
     * Checks to see if current user is the winner.
     * if item is not expired and Forward auction type, returns to forward bid screen
     * if item is not expired and Dutch, returns to Dutch bid screen
     * if neither expired, Dutch or Forward, returns error.
     */
    @PostMapping("/bid")
    public String handleBid(@RequestParam("itemId") Long itemId, Model model, HttpSession session) {
        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error"; 
        }

        catalogueService.enrichItem(item);

        if ("Expired".equalsIgnoreCase(item.getAuctionType())) {
            User highestBidder = auctionService.getHighestBidder(item);
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            model.addAttribute("item", item);
            model.addAttribute("highestBidder", highestBidder);

            boolean isWinner = (highestBidder != null && loggedInUser != null && highestBidder.getId().equals(loggedInUser.getId()));
            model.addAttribute("isWinner", isWinner);

            return "pre-payment";
        }

        if ("Forward".equalsIgnoreCase(item.getAuctionType())) {
            User highestBidder = auctionService.getHighestBidder(item);
            model.addAttribute("item", item);
            model.addAttribute("highestBidder", highestBidder);

            return "Forward-bidding"; 
        } else if ("Dutch".equalsIgnoreCase(item.getAuctionType())) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                model.addAttribute("error", "You must be logged in to proceed.");
                return "login"; 
            }

            model.addAttribute("item", item);
            model.addAttribute("highestBidder", loggedInUser); 
            return "Dutch-bidding"; 
        } else {
            model.addAttribute("error", "Unknown auction type.");
            return "error";
        }
    }


    //placeBid page
    
    @PostMapping("/placeBid")
    public String placeBid(@RequestParam("itemId") Long itemId,
                           @RequestParam("bidAmount") double amount,
                           HttpSession session, Model model) {
    	/*
         * Retrieves the logged-in user from the session
         * If no user, redirects to login screen
         * if an item is null, return a error
         */
    	User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "You must be logged in to place a bid.");
            return "login"; 
        }

        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error";
        }
        /*
         * Checks to see if the bid is valid.
         * if the bid is valid, update parameters.
         * enriches the item with remaining time to ensure that parameters are updated.
         * returns to forward bidding.
         */
        if (amount <= item.getCurrentPrice()) {
            model.addAttribute("error", "Your bid must be higher than the current price.");
            User highestBidder = auctionService.getHighestBidder(item);
            model.addAttribute("item", item);

            catalogueService.enrichItem(item);
            model.addAttribute("highestBidder", highestBidder);

            return "Forward-bidding"; 
        }

        Bid bid = auctionService.placeBid(user, item, amount);

        if (bid == null) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
        } else {
            model.addAttribute("success", "Bid placed successfully.");
        }

        catalogueService.enrichItem(item);

        User highestBidder = auctionService.getHighestBidder(item);
        model.addAttribute("item", item);
        model.addAttribute("highestBidder", highestBidder);

        return "Forward-bidding";
    }

    
    
    /*
     * prePayment screen:
     * checks to make sure user is logged in.
     * retrieves item by itermId (if item not found returns error)
     * for Dutch Auction, user who pressed 'Buy Now' is highestBidder
     * if Forward, find highestBidder
     * checks to see if current session user is highestBidder
     *
     */
    @PostMapping("/prePayment")
    public String showPrePaymentPage(@RequestParam("itemId") Long itemId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "You must be logged in to proceed.");
            return "login"; 
        }

        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error"; 
        }

        User highestBidder = null;
        if ("Dutch".equalsIgnoreCase(item.getAuctionType())) {
            highestBidder = user; 
        } else if ("Forward".equalsIgnoreCase(item.getAuctionType())) {
            highestBidder = auctionService.getHighestBidder(item);
        }

        model.addAttribute("item", item);
        model.addAttribute("highestBidder", highestBidder);

        double expeditedShippingCost = item.getExpeditedShippingCost();
        model.addAttribute("expeditedShippingCost", expeditedShippingCost);

        boolean isWinner = (highestBidder != null && highestBidder.getId().equals(user.getId()));
        model.addAttribute("isWinner", isWinner);

        return "pre-payment"; 
    }

    /*
     * GET Requests for prePayment
     */
    @GetMapping("/prePayment")
    public String showPrePaymentPageForGet(@RequestParam("itemId") Long itemId, HttpSession session, Model model) {
        return showPrePaymentPage(itemId, session, model);
    }

    
    /*
     * buyNow screen
     * checks that the user and item are both not null
     * if the auction is Dutch the user is the highestBidder automatically
     * get shippingCost, and send to pre-payment
     */
    @PostMapping("/buyNow")
    public String buyNow(@RequestParam("itemId") Long itemId,
                         HttpSession session, Model model) {
      
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "You must be logged in to buy now.");
            return "login"; 
        }

        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error";
        }

 
        if ("Dutch".equalsIgnoreCase(item.getAuctionType())) {
            item.setHighestBidder(user); 
            catalogueService.saveItem(item); 
        }

        model.addAttribute("item", item);
        model.addAttribute("highestBidder", user);

        double expeditedShippingCost = item.getExpeditedShippingCost();
        model.addAttribute("expeditedShippingCost", expeditedShippingCost);

        model.addAttribute("isWinner", true);

        return "pre-payment"; 
    }

    /*
     * payNow screen
     * gets logged in user info (if null, return error)
     * gets item from catalogue (if item not found returns error)
     * makes sure current session user is highestBidder, if not redirects to pre-payment(error message)
     * calculates final price with shipping
     * proceeds to payment page if user is the highest bidder
     */
    @PostMapping("/payNow")
    public String payNow(@RequestParam("itemId") Long itemId,
                         @RequestParam("shippingOption") String shippingOption,
                         HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("error", "You must be logged in to pay.");
            return "login";
        }

        Item item = catalogueService.getItemById(itemId);
        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error";
        }

        User highestBidder = item.getHighestBidder();
        if (highestBidder == null || !highestBidder.getId().equals(user.getId())) {
        
            model.addAttribute("item", item);
            model.addAttribute("highestBidder", highestBidder);
            model.addAttribute("error", "You are not the winning bidder for this auction, so you cannot proceed to payment.");
            return "pre-payment"; 
        }


        double totalPrice = item.getCurrentPrice();
        if ("expedited".equalsIgnoreCase(shippingOption)) {
            totalPrice += item.getExpeditedShippingCost();
        } else {
            totalPrice += item.getShippingPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("item", item);
        model.addAttribute("shippingOption", shippingOption);
        model.addAttribute("user", user);

        return "payment"; 
    }


    /*
     * handleExpiredAuction page
     * gets logged in user (if null redirects to login)
     * gets item from catalogue(if null returns error)
     * if item is still active, redirects to error page
     * if user is the winner, send to pre-payment
     */
    @PostMapping("/handleExpiredAuction")
    public String handleExpiredAuction(@RequestParam("itemId") Long itemId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "You must be logged in to proceed.");
            return "login"; 
        }

        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "error"; 
        }

        catalogueService.enrichItem(item);
        if (!"Expired".equalsIgnoreCase(item.getRemainingTime())) {
            model.addAttribute("error", "The auction is still active.");
            return "error"; 
        }

        User highestBidder = auctionService.getHighestBidder(item);

        model.addAttribute("item", item);
        model.addAttribute("highestBidder", highestBidder);

        boolean isWinner = (highestBidder != null && highestBidder.getId().equals(user.getId()));
        model.addAttribute("isWinner", isWinner);

        return "pre-payment"; 
    }
    
    
    /*
     * GET requests for checkAuctionStatus
     */
    @GetMapping("/checkAuctionStatus")
    @ResponseBody
    public Map<String, Boolean> checkAuctionStatus(@RequestParam("itemId") Long itemId) {
        Map<String, Boolean> response = new HashMap<>();
        Item item = catalogueService.getItemById(itemId);

        if (item != null) {
            catalogueService.enrichItem(item); 
            response.put("auctionExpired", "Expired".equalsIgnoreCase(item.getRemainingTime()));
        } else {
            response.put("auctionExpired", false);
        }

        return response;
    }
    
    
    /*
     * Get Requests for getRemainingTime
     */
    @GetMapping("/getRemainingTime")
    @ResponseBody
    public Map<String, String> getRemainingTime(@RequestParam("itemId") Long itemId) {
        Map<String, String> response = new HashMap<>();
        
        if (itemId == null) {
            response.put("remainingTime", "Error: Missing itemId");
            return response;
        }

        Item item = catalogueService.getItemById(itemId);
        if (item != null) {
            catalogueService.enrichItem(item); 
            response.put("remainingTime", item.getRemainingTime());
        } else {
            response.put("remainingTime", "Expired");
        }

        return response;
    }


    
    /*
     * GET requests for getCurrentPrice
     */
    @GetMapping("/getCurrentPrice")
    @ResponseBody
    public Map<String, Object> getCurrentPrice(@RequestParam("itemId") Long itemId) {
        Map<String, Object> response = new HashMap<>();
        Item item = catalogueService.getItemById(itemId);

        if (item != null) {
            response.put("currentPrice", item.getCurrentPrice());
        } else {
            response.put("currentPrice", null); 
        }

        return response;
    }
    /*
     * 	GET Requests for getHighestBidder
     */
    @GetMapping("/getHighestBidder")
    @ResponseBody
    public Map<String, Object> getHighestBidder(@RequestParam("itemId") Long itemId) {
        Map<String, Object> response = new HashMap<>();
        Item item = catalogueService.getItemById(itemId);

        if (item != null) {
            User highestBidder = auctionService.getHighestBidder(item);
            response.put("highestBidder", highestBidder != null ? highestBidder.getUsername() : null);
        } else {
            response.put("highestBidder", null); // No highest bidder if item is null
        }

        return response;
    }

    
    

}
