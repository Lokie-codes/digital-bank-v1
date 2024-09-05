package com.digitalbank.transaction_service.clients;

import com.digitalbank.transaction_service.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "accounts-service",url = "http://localhost:8081/api")
public interface UserClient {
    @GetMapping("/accounts/{id}")
    Optional<UserResponse> findById(@PathVariable("id") Long id);
}
