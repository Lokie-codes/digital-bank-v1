package com.digitalbank.cards_service.controllers;

import com.digitalbank.cards_service.models.CreditCard;
import com.digitalbank.cards_service.models.DebitCard;
import com.digitalbank.cards_service.services.CreditCardService;
import com.digitalbank.cards_service.services.DebitCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    private final DebitCardService debitCardService;
    private final CreditCardService creditCardService;
    public CardController(DebitCardService debitCardService, CreditCardService creditCardService) {
        this.debitCardService = debitCardService;
        this.creditCardService = creditCardService;
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

    @GetMapping("/cards/credit-card/{cardId}")
    public ResponseEntity<?> getCreditCard(@PathVariable Long cardId) {
        return creditCardService.getCreditCardById(cardId);
    }

    @PostMapping("/cards/credit-card")
    public ResponseEntity<?> createCreditCard(@RequestParam Long catalogueId, @RequestParam Long userId) {
        return creditCardService.createCreditCard(catalogueId, userId);
    }

    @GetMapping("/cards/credit-card/users/{userId}")
    public ResponseEntity<?> getALlCreditCards(@PathVariable Long userId) {
        return creditCardService.listAllCreditCards(userId);
    }

    @PutMapping("/cards/credit-card/{cardId}")
    public ResponseEntity<?> updateCreditCard(@PathVariable Long cardId, @RequestBody CreditCard card) {
        return creditCardService.updateCreditCard(cardId, card);
    }

    @DeleteMapping("/cards/credit-card/{cardId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable Long cardId) {
        return creditCardService.deleteCreditCard(cardId);
    }

}
