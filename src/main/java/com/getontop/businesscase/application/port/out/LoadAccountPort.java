package com.getontop.businesscase.application.port.out;

import java.time.LocalDateTime;

import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Account.AccountId;


public interface LoadAccountPort {

	Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
