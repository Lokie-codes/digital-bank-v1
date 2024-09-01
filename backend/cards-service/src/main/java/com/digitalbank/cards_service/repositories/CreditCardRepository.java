package com.digitalbank.cards_service.repositories;

import com.digitalbank.cards_service.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
