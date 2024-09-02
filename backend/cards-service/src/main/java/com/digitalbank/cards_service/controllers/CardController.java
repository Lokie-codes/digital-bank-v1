package com.digitalbank.cards_service.controllers;

import com.digitalbank.cards_service.models.DebitCard;
import com.digitalbank.cards_service.services.DebitCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    private final DebitCardService debitCardService;
    public CardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }

    @GetMapping("/cards/debit-card/{id}")
    public ResponseEntity<?> getDebitCard(@PathVariable Long id) {
        return debitCardService.getDebitCard(id);
    }

    @PostMapping("/cards/debit-card")
    public ResponseEntity<?> createDebitCard(@RequestParam Integer accountNumber) {
        return debitCardService.createDebitCard(accountNumber);
    }

    @PutMapping("/cards/debit-card/{id}")
    public ResponseEntity<?> updateDebitCard(@PathVariable Long id, @RequestBody DebitCard card) {
        return debitCardService.updateDebitCardDetails(id, card);
    }

    @DeleteMapping("/cards/debit-card/{id}")
    public ResponseEntity<?> deleteDebitCard(@PathVariable Long id) {
        return debitCardService.deleteDebitCard(id);
    }

}
