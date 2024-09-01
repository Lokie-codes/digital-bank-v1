package com.digitalbank.cards_service.clients;

import com.digitalbank.cards_service.responses.UserResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "UserService", url = "http://localhost:8082/api")
public interface UserClient {
    @GetMapping("/users/{id}")
    Optional<UserResponse> findById(@PathVariable("id") Long id);
}
