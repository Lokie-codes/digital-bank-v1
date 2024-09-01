package com.digitalbank.loan_service.clients;

import com.digitalbank.loan_service.responses.UserResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "http://localhost:8082/api")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserResponse findById(@PathVariable("id") Long id);
}
