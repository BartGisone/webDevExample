package com.HBauction.webapp.service;

import com.HBauction.webapp.model.User;
import com.HBauction.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /*
     * User registration
     * If username already exists, throws an exception.
     * if username is unique, saves user.
     */
    public User register(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("Username already exists.");
        }
        return userRepository.save(user); // Save the user if no conflict
    }

    /*
     * logs in a user by searching for matching username and password.
     * if a user is not found, the system will return null (userNotFound)
     */
    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.orElse(null); // Return the user if credentials match; otherwise, null
    }
    
    /*
     * searches the repository to find User by userID
     * if user not found, returns null (userNotFound)
     */
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }


    
    

    
}
