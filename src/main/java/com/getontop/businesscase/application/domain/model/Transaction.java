package com.getontop.businesscase.application.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * A money transfer transaction between {@link Account}s.
 */
@Value
@RequiredArgsConstructor
public class Transaction {

	@Getter
	private TransactionId id;

	/**
	 * The account that owns this transaction.
	 */
	@Getter
	@NonNull
	private final Account.AccountId ownerAccountId;

	/**
	 * The debited account.
	 */
	@Getter
	@NonNull
	private final Account.AccountId sourceAccountId;

	/**
	 * The credited account.
	 */
	@Getter
	@NonNull
	private final Account.AccountId targetAccountId;

	/**
	 * The timestamp of the transaction.
	 */
	@Getter
	@NonNull
	private final LocalDateTime timestamp;

	/**
	 * The money that was transferred between the accounts.
	 */
	@Getter
	@NonNull
	private final Money money;
	
	@Getter
	private final String status;

	public Transaction(
			@NonNull Account.AccountId ownerAccountId,
			@NonNull Account.AccountId sourceAccountId,
			@NonNull Account.AccountId targetAccountId,
			@NonNull LocalDateTime timestamp,
			@NonNull Money money,
			@NonNull String status) {
		this.id = null;
		this.ownerAccountId = ownerAccountId;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.timestamp = timestamp;
		this.money = money;
		this.status = status;
	}

	@Value
	public static class TransactionId {
		private final Long value;
	}

}
