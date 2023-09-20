package com.getontop.businesscase.application.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * An account that holds a certain amount of money. An {@link Account} object only
 * contains a window of the latest account activities. The total balance of the account is
 * the sum of a baseline balance that was valid before the first transaction in the
 * window and the sum of the transaction values.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

	/**
	 * The unique ID of the account.
	 */
	private final AccountId id;

	/**
	 * The baseline balance of the account. This was the balance of the account before the first
	 * transaction in the transactionWindow.
	 */
	@Getter private final Money baselineBalance;

	/**
	 * The window of latest activities on this account.
	 */
	@Getter private final TransactionWindow transactionWindow;

	/**
	 * Creates an {@link Account} entity without an ID. Use to create a new entity that is not yet
	 * persisted.
	 */
	public static Account withoutId(
					Money baselineBalance,
					TransactionWindow transactionWindow) {
		return new Account(null, baselineBalance, transactionWindow);
	}

	/**
	 * Creates an {@link Account} entity with an ID. Use to reconstitute a persisted entity.
	 */
	public static Account withId(
					AccountId accountId,
					Money baselineBalance,
					TransactionWindow transactionWindow) {
		return new Account(accountId, baselineBalance, transactionWindow);
	}

	public Optional<AccountId> getId(){
		return Optional.ofNullable(this.id);
	}

	/**
	 * Calculates the total balance of the account by adding the transaction values to the baseline balance.
	 */
	public Money calculateBalance() {
		return Money.add(
				this.baselineBalance,
				this.transactionWindow.calculateBalance(this.id));
	}

	/**
	 * Tries to withdraw a certain amount of money from this account.
	 * If successful, creates a new transaction with a negative value.
	 * @return true if the withdrawal was successful, false if not.
	 */
	public boolean withdraw(Money money, AccountId targetAccountId) {

		if (!mayWithdraw(money)) {
			return false;
		}		
		
		/**
		 * calculation feee and apply into new transaction
		 */
		Transaction withdrawal = new Transaction(
				this.id,
				this.id,
				targetAccountId,
				LocalDateTime.now(),
				money.minus(money.fee()),
				"Success");
 		this.transactionWindow.addTransaction(withdrawal);
		return true;
	}

	public boolean mayWithdraw(Money money) {
		return Money.add(
				this.calculateBalance(),
				money.negate())
				.isPositiveOrZero();
	}

	/**
	 * Tries to deposit a certain amount of money to this account.
	 * If sucessful, creates a new transaction with a positive value.
	 * @return true if the deposit was successful, false if not.
	 */
	public boolean deposit(Money money, AccountId sourceAccountId) {
		Transaction deposit = new Transaction(
				this.id,
				sourceAccountId,
				this.id,
				LocalDateTime.now(),
				money.minus(money.fee()),
				"Success");
		this.transactionWindow.addTransaction(deposit);
		return true;
	}

	@Value
	public static class AccountId {
		private Long value;
	}

}
