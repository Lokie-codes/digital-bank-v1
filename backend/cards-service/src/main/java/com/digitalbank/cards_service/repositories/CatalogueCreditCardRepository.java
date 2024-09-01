package com.digitalbank.cards_service.repositories;

import com.digitalbank.cards_service.models.CatalogueCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueCreditCardRepository extends JpaRepository<CatalogueCreditCard, Long> {
}
