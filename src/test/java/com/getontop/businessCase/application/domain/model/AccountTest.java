package com.getontop.businessCase.application.domain.model;

import org.junit.jupiter.api.Test;

import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.domain.model.TransactionWindow;
import com.getontop.businesscase.application.domain.model.Money;

import static com.getontop.businessCase.util.AccountTestData.*;
import static com.getontop.businessCase.util.TransactionTestData.*;
import static org.assertj.core.api.Assertions.*;

class AccountTest {

	@Test
	void calculatesBalance() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount().withAccountId(accountId).withBaselineBalance(Money.of(555L))
				.withTransactionWindow(new TransactionWindow(
						defaultTransaction().withTargetAccount(accountId).withMoney(Money.of(999L)).build(),
						defaultTransaction().withTargetAccount(accountId).withMoney(Money.of(1L)).build()))
				.build();

		Money balance = account.calculateBalance();

		assertThat(balance).isEqualTo(Money.of(1555L));
	}

	@Test
	void withdrawalFailure() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount().withAccountId(accountId).withBaselineBalance(Money.of(555L))
				.withTransactionWindow(new TransactionWindow(
						defaultTransaction().withTargetAccount(accountId).withMoney(Money.of(999L)).build(),
						defaultTransaction().withTargetAccount(accountId).withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.withdraw(Money.of(1556L), new AccountId(99L));

		assertThat(success).isFalse();
		assertThat(account.getTransactionWindow().getTransactions()).hasSize(2);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(1555L));
	}

}