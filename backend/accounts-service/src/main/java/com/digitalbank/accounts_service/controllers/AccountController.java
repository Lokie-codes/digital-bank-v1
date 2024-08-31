package com.digitalbank.accounts_service.controllers;

import com.digitalbank.accounts_service.models.Account;
import com.digitalbank.accounts_service.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/accounts/search")
    public ResponseEntity<?> searchAccounts(
            @RequestParam(required = false) int accountNumber,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) String currency) {
        return accountService.searchAccounts(accountNumber, customerId, accountType, currency);
    }

}
