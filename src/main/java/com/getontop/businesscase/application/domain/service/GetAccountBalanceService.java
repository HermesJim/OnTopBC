package com.getontop.businesscase.application.domain.service;


import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.port.in.GetAccountBalanceUseCase;
import com.getontop.businesscase.application.port.out.LoadAccountPort;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceUseCase {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(GetAccountBalanceQuery query) {
		return loadAccountPort.loadAccount(query.accountId(), LocalDateTime.now())
				.calculateBalance();
	}
}
