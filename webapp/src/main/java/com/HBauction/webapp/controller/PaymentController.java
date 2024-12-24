package com.HBauction.webapp.controller;

import com.HBauction.webapp.model.Item; 
import com.HBauction.webapp.model.Payment;
import com.HBauction.webapp.model.User;
import com.HBauction.webapp.service.CatalogueService;
import com.HBauction.webapp.service.PaymentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CatalogueService catalogueService;
    

    /*
     * GET requests for payment page
     */
    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam("itemId") Long itemId,
                                  HttpSession session, Model model) {
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

        model.addAttribute("user", user);
        model.addAttribute("item", item);
        return "payment";
    }


    /*
     * submitPayment Page
     * gets credit card information and processes payment
     */
    @PostMapping("/submitPayment")
    public String submitPayment(@RequestParam("itemId") Long itemId,
                                 @RequestParam("cardNumber") String cardNumber,
                                 @RequestParam("cardHolderName") String cardHolderName,
                                 @RequestParam("expiryDate") String expiryDate,
                                 @RequestParam("securityCode") String securityCode,
                                 @SessionAttribute("loggedInUser") User user, Model model) {
        Item item = catalogueService.getItemById(itemId);

        if (item == null) {
            model.addAttribute("error", "Item not found.");
            return "payment";
        }

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setItem(item);
        payment.setAmountPaid(item.getCurrentPrice());
        payment.setCardNumber(cardNumber);
        payment.setCardHolderName(cardHolderName);
        payment.setExpiryDate(expiryDate);
        payment.setSecurityCode(securityCode);

        paymentService.processPayment(payment);

        model.addAttribute("payment", payment);
        model.addAttribute("item", item);
        model.addAttribute("user", user);

        return "receipt"; 
        }
}
