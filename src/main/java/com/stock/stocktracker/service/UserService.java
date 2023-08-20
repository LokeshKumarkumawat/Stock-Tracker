package com.stock.stocktracker.service;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.stock.stocktracker.entity.User;
import com.stock.stocktracker.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.stock.stocktracker.utils.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User createUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
//        newUser.setPassword(passwordEncoder.encode(password)); // Hash the password
        newUser.setPassword(password); // Hash the password

        return userRepository.save(newUser);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Other user-related methods...

    // For updating user details, you might add a method like this
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = getUserById(userId);
        existingUser.setUsername(updatedUser.getUsername());
        // Update other fields as needed
        return userRepository.save(existingUser);
    }

    // For deleting a user, you might add a method like this
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
