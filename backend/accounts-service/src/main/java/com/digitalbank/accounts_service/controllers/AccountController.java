package com.digitalbank.accounts_service.controllers;

import com.digitalbank.accounts_service.DTO.AccountDTO;
import com.digitalbank.accounts_service.models.Account;
import com.digitalbank.accounts_service.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }


    @GetMapping("/accounts")
    public ResponseEntity<?> listAccounts() {
        return accountService.getAllAccounts();
    }
}
