package com.digitalbank.accounts_service.clients;

import com.digitalbank.accounts_service.DTO.UserDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "http://localhost:8082/api")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable("id") Long id);
}
