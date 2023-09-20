package com.getontop.businesscase.adapter.persistence;

import org.springframework.stereotype.Component;

import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.port.out.AccountLock;

@Component
class NoOpAccountLock implements AccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}

}
