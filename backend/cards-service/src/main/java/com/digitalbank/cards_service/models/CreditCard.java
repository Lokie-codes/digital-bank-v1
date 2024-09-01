package com.digitalbank.cards_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCard extends Card {
    private String cardName;
    private String cardType;
    private Double creditLimit;
    private Double availableLimit;
    private String cardClass;
    @Temporal(TemporalType.DATE)
    private Date billGenerationDate;
    @Temporal(TemporalType.DATE)
    private Date billDueDate;
    private BigInteger billAmount;
    private BigInteger paymentBalanceAmount;
}
