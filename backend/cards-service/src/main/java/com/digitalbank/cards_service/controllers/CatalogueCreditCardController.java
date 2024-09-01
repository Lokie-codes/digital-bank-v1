package com.digitalbank.cards_service.controllers;

import com.digitalbank.cards_service.models.CatalogueCreditCard;
import com.digitalbank.cards_service.services.CatalogueCreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CatalogueCreditCardController {
    private final CatalogueCreditCardService catalogueCreditCardService;
    public CatalogueCreditCardController(CatalogueCreditCardService catalogueCreditCardService) {
        this.catalogueCreditCardService = catalogueCreditCardService;
    }

    @GetMapping("/credit-cards")
    public ResponseEntity<?> getAllCreditCards() {
        return catalogueCreditCardService.listAllCreditCards();
    }

    @PostMapping("/credit-cards")
    public ResponseEntity<?> createCreditCard(@RequestBody CatalogueCreditCard creditCard) {
        return catalogueCreditCardService.addCreditCard(creditCard);
    }

    @GetMapping("/credit-cards/{id}")
    public ResponseEntity<?> getCreditCardById(@PathVariable Long id) {
        return catalogueCreditCardService.fetchCreditCardById(id);
    }

    @PutMapping("/credit-cards/{id}")
    public ResponseEntity<?> updateCreditCard(@PathVariable Long id, @RequestBody CatalogueCreditCard creditCard) {
        return catalogueCreditCardService.updateCreditCard(id, creditCard);
    }

    @DeleteMapping("/credit-cards/{id}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable Long id) {
        return catalogueCreditCardService.deleteCreditCard(id);
    }

}
