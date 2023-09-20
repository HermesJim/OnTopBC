package com.getontop.businesscase.application.port.out;

import com.getontop.businesscase.application.domain.model.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}
