package com.getontop.businesscase.application.port.in;

import static com.getontop.businesscase.common.validator.Validation.validate;

import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;

import jakarta.validation.constraints.NotNull;


public record TransactionCommand(
        @NotNull AccountId sourceAccountId,
        @NotNull AccountId targetAccountId,
        @NotNull @PositiveMoney Money money
) {

    public TransactionCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        validate(this);
    }

}
