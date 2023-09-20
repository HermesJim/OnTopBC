package com.getontop.businesscase.application.port.in;

import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;

public interface GetAccountBalanceUseCase {

	Money getAccountBalance(GetAccountBalanceQuery query);

	record GetAccountBalanceQuery(AccountId accountId) {
	}
}
