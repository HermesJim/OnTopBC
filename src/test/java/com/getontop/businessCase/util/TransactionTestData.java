package com.getontop.businessCase.util;

import java.time.LocalDateTime;

import com.getontop.businesscase.application.domain.model.Transaction;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.domain.model.Transaction.TransactionId;


public class TransactionTestData {

	public static TransactionBuilder defaultTransaction(){
		return new TransactionBuilder()
				.withOwnerAccount(new AccountId(42L))
				.withSourceAccount(new AccountId(42L))
				.withTargetAccount(new AccountId(41L))
				.withTimestamp(LocalDateTime.now())
				.withMoney(Money.of(999L))
				.withStatus("Success");
	}

	public static class TransactionBuilder {
		private TransactionId id;
		private AccountId ownerAccountId;
		private AccountId sourceAccountId;
		private AccountId targetAccountId;
		private LocalDateTime timestamp;
		private Money money;
		private String status;

		public TransactionBuilder withId(TransactionId id) {
			this.id = id;
			return this;
		}

		public TransactionBuilder withOwnerAccount(AccountId accountId) {
			this.ownerAccountId = accountId;
			return this;
		}

		public TransactionBuilder withSourceAccount(AccountId accountId) {
			this.sourceAccountId = accountId;
			return this;
		}

		public TransactionBuilder withTargetAccount(AccountId accountId) {
			this.targetAccountId = accountId;
			return this;
		}

		public TransactionBuilder withTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public TransactionBuilder withMoney(Money money) {
			this.money = money;
			return this;
		}
		
		public TransactionBuilder withStatus(String status) {
			this.status = status;
			return this;
		}

		public Transaction build() {
			return new Transaction(
					this.id,
					this.ownerAccountId,
					this.sourceAccountId,
					this.targetAccountId,
					this.timestamp,
					this.money,
					this.status);
		}
	}
}
