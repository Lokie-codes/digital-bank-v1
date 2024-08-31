package com.digitalbank.user_service.controllers;

import com.digitalbank.user_service.models.User;
import com.digitalbank.user_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return userService.createUser(user);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/change-username")
    public ResponseEntity<?> changeUsername(
            @PathVariable Long id) {
        return userService.changeUsername(id);
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> searchUser(
            @RequestParam(required = false) String emailId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String username) {
        if(emailId != null) {
            return userService.getUserByEmail(emailId);
        } else if(phoneNumber != null) {
            return userService.getUserByPhone(phoneNumber);
        } else if(username != null) {
            return userService.getUserByUsername(username);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("requestParam", "Request parameter invalid.");
            return ResponseEntity.badRequest().body(errors);
        }
    }



}
