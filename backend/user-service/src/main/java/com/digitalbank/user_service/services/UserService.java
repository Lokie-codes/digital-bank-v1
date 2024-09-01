package com.digitalbank.user_service.services;

import com.digitalbank.user_service.DTO.UserDTO;
import com.digitalbank.user_service.clients.AgentNameClient;
import com.digitalbank.user_service.models.AgentName;
import com.digitalbank.user_service.models.User;
import com.digitalbank.user_service.repositories.UserRepository;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import java.util.*;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Pattern emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    private final AgentNameClient agentNameClient;

    public UserService(
        UserRepository userRepository,
        AgentNameClient agentNameClient
    ) {
        this.userRepository = userRepository;
        this.agentNameClient = agentNameClient;
    }

    public ResponseEntity<?> createUser(User user) {
        Map<String, String> errors = validateUser(user);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        sanitizeUser(user);
        User savedUser = userRepository.save(user);
        UserDTO userDTO = convertToDTO(savedUser);
        return ResponseEntity.ok(userDTO);
    }

    private void sanitizeUser(User user) {
        user.setEmail(user.getEmail().toLowerCase(Locale.ROOT));
        user.setFirstName(user.getFirstName().toLowerCase(Locale.ROOT));
        user.setLastName(user.getLastName().toLowerCase(Locale.ROOT));
        user.setUsername(generateUsername(user.getEmail()));
    }

    private String generateUsername(String fallbackUsername) {
        if (agentNameClient == null) {
            System.out.println(
                "Agent name generating service not found. Setting username to email."
            );
            return fallbackUsername.toLowerCase(Locale.ROOT);
        }

        try {
            String username;
            do {
                AgentName agentName = agentNameClient.generateAgentName();
                username = agentName.getName().toLowerCase(Locale.ROOT);
            } while (userRepository.findByUsername(username).isPresent());
            return username;
        } catch (BadRequestException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Improper request. Please check the request for Agent name generating service."
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Requested resource not found for Agent name generating service."
            );
        } catch (WebApplicationException e) {
            System.out.println(
                "Agent name generating service is down. Setting username to email."
            );
            return fallbackUsername.toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            System.out.println(
                "An error occurred while generating username. Setting username to email."
            );
            return fallbackUsername.toLowerCase(Locale.ROOT);
        }
    }

    private Map<String, String> validateUser(User user) {
        Map<String, String> errors = new HashMap<>();
        validateEmail(user.getEmail(), errors);
        validateField("firstName", user.getFirstName(), errors);
        validateField("lastName", user.getLastName(), errors);
        validatePhone(user.getPhone(), errors);
        validatePassword(user.getPassword(), errors);
        return errors;
    }

    private void validateEmail(String email, Map<String, String> errors) {
        if (email == null || email.isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!isEmailValid(email)) {
            errors.put("email", "Invalid email format");
        } else if (userRepository.findByEmail(email).isPresent()) {
            errors.put(
                "email",
                "Email is already in use. Please try a different email"
            );
        }
    }

    private void validateField(
        String fieldName,
        String value,
        Map<String, String> errors
    ) {
        if (value == null || value.isEmpty()) {
            errors.put(fieldName, fieldName + " is required");
        }
    }

    private void validatePhone(String phone, Map<String, String> errors) {
        if (phone == null || phone.isEmpty()) {
            errors.put("phone", "Phone is required");
        } else if (!phone.matches("^[+][0-9]{10,13}$")) {
            errors.put(
                "phone",
                "Enter a valid phone number with country code (e.g., +919876543210)"
            );
        }
    }

    private void validatePassword(String password, Map<String, String> errors) {
        if (password == null || password.isEmpty()) {
            errors.put("password", "Password is required");
        } else if (!isPasswordValid(password)) {
            errors.put(
                "password",
                "Password must be 8-16 characters long, contain at least one digit, one uppercase letter, and one special character"
            );
        }
    }

    private boolean isEmailValid(String email) {
        return emailPattern.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return (
            password.length() >= 8 &&
            password.length() <= 16 &&
            password.matches(".*\\d.*") &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[._!@#$%^&*()?,].*")
        );
    }

    public ResponseEntity<?> getUserById(Long id) {
        if (!userRepository.existsById(id)) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "User with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
        return ResponseEntity.ok(
            convertToDTO(userRepository.getReferenceById(id))
        );
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                Map.of("error", "No users found")
            );
        }

        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> userDTOs.add(convertToDTO(user)));
        return ResponseEntity.ok(userDTOs);
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "User with email " + email + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
        return ResponseEntity.ok(convertToDTO(user.get()));
    }

    public ResponseEntity<?> getUserByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "User with phone " + phone + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
        return ResponseEntity.ok(convertToDTO(user.get()));
    }

    public ResponseEntity<?> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                "error",
                "User with username " + username + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
        return ResponseEntity.ok(convertToDTO(user.get()));
    }

    public ResponseEntity<?> changeUsername(Long id) {
        User user = userRepository.getReferenceById(id);
        user.setUsername(
            generateUsername(user.getUsername()).toLowerCase(Locale.ROOT)
        );
        userRepository.save(user);
        return ResponseEntity.ok(convertToDTO(user));
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }
}
