package com.digitalbank.transaction_service.clients;

import com.digitalbank.transaction_service.responses.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "account-service", url = "http://localhost:8081/api")
public interface AccountClient {
    @GetMapping("/accounts/{id}")
    Optional<AccountResponse> findById(@PathVariable("id") Long id);

}
