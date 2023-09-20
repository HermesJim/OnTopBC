package com.getontop.businessCase.application.port.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.port.in.TransactionCommand;

import java.math.BigDecimal;

class SendMoneyCommandTest {

    @Test
    public void validationOk() {
        new TransactionCommand(
                new Account.AccountId(42L),
                new Account.AccountId(43L),
                new Money(BigDecimal.valueOf(10.0)));
    }

    @Test
    public void moneyValidationFails() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            new TransactionCommand(
                    new Account.AccountId(42L),
                    new Account.AccountId(43L),
                    new Money(BigDecimal.valueOf(-10.0)));
        });
    }

    @Test
    public void accountIdValidationFails() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            new TransactionCommand(
                    new Account.AccountId(42L),
                    null,
                    new Money(BigDecimal.valueOf(10.0)));
        });
    }

}