package com.getontop.businessCase.util;

import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.TransactionWindow;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;

public class AccountTestData {

	public static AccountBuilder defaultAccount() {
		return new AccountBuilder()
				.withAccountId(new AccountId(42L))
				.withBaselineBalance(Money.of(999L))
				.withTransactionWindow(new TransactionWindow(
						TransactionTestData.defaultTransaction().build(),
						TransactionTestData.defaultTransaction().build()));
	}


	public static class AccountBuilder {

		private AccountId accountId;
		private Money baselineBalance;
		private TransactionWindow transactionWindow;

		public AccountBuilder withAccountId(AccountId accountId) {
			this.accountId = accountId;
			return this;
		}

		public AccountBuilder withBaselineBalance(Money baselineBalance) {
			this.baselineBalance = baselineBalance;
			return this;
		}

		public AccountBuilder withTransactionWindow(TransactionWindow transactionWindow) {
			this.transactionWindow = transactionWindow;
			return this;
		}

		public Account build() {
			return Account.withId(this.accountId, this.baselineBalance, this.transactionWindow);
		}

	}


}
