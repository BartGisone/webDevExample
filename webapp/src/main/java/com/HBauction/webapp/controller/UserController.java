package com.HBauction.webapp.controller;

import com.HBauction.webapp.model.User;
import com.HBauction.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /*
     *  login page
     *  returns 'success' if successful login
     */
    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute("success") String success, Model model) {
        model.addAttribute("success", success); 
        return "login"; 
    }

    //	Signup page
    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup"; 
    }


    /*
     * 	Signup page
     * processes input data and returns 'success' if successful
     */
    @PostMapping("/signup")
    public String processSignUp(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String streetAddress,
            @RequestParam String streetNumber,
            @RequestParam String postalCode,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String country,
            RedirectAttributes redirectAttributes) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setStreetAddress(streetAddress);
            user.setStreetNumber(streetNumber);
            user.setPostalCode(postalCode);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);

            userService.register(user);
            redirectAttributes.addFlashAttribute("success", "Account created successfully! Please log in.");
            return "redirect:/login"; // Redirect to login page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Try again.");
            return "redirect:/signup"; 
        }
    }

    /*
     * login page
     * if user is null user receives an error message.
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session, Model model) {
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/search"; 
    }



}
