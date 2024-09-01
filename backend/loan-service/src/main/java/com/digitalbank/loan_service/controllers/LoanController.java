package com.digitalbank.loan_service.controllers;

import com.digitalbank.loan_service.models.Loan;
import com.digitalbank.loan_service.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoanController {
    private final LoanService loanService;
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loans")
    public ResponseEntity<?> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping("/loans")
    public ResponseEntity<?> createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Long id) {
        return loanService.getLoan(id);
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        return loanService.updateLoan(id, loan);
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id) {
        return loanService.deleteLoan(id);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> applyForLoan(
            @PathVariable Long userId,
            @RequestParam(required = true) Long loanId,
            @RequestParam(required = true) int tenureMonths) {
        return loanService.applyForLoan(userId, loanId, tenureMonths);
    }

    @GetMapping("/users/{userId}/loans")
    public ResponseEntity<?> getUserLoans(@PathVariable Long userId) {
        return loanService.fetchUserLoans(userId);
    }

}
